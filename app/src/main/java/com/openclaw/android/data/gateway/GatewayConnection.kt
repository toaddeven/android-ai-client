package com.openclaw.android.data.gateway

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GatewayConnection @Inject constructor(
    private val gson: Gson
) {
    private var webSocket: WebSocketClient? = null
    
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()
    
    private val _messages = MutableSharedFlow<Message>()
    val messages: SharedFlow<Message> = _messages.asSharedFlow()
    
    private val _responses = MutableSharedFlow<Response>()
    val responses: SharedFlow<Response> = _responses.asSharedFlow()
    
    private var gatewayUrl: String = "ws://127.0.0.1:18789"
    private var authToken: String = ""
    private var deviceId: String = ""
    
    data class Message(
        val id: String,
        val type: String,
        val content: String,
        val timestamp: Long = System.currentTimeMillis()
    )
    
    data class Response(
        val id: String,
        val type: String,
        val data: JsonObject?
    )
    
    sealed class ConnectionState {
        object Disconnected : ConnectionState()
        object Connecting : ConnectionState()
        object Connected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }
    
    fun configure(url: String, token: String, deviceId: String) {
        this.gatewayUrl = url
        this.authToken = token
        this.deviceId = deviceId
    }
    
    fun connect() {
        if (_connectionState.value == ConnectionState.Connected) return
        
        _connectionState.value = ConnectionState.Connecting
        
        try {
            val wsUrl = gatewayUrl.replace("http", "ws") + "/ws"
            val uri = URI("$wsUrl?token=$authToken&deviceId=$deviceId")
            
            webSocket = object : WebSocketClient(uri) {
                override fun onOpen(handshake: ServerHandshake) {
                    _connectionState.value = ConnectionState.Connected
                }
                
                override fun onMessage(message: String) {
                    handleMessage(message)
                }
                
                override fun onClose(code: Int, reason: String, remote: Boolean) {
                    _connectionState.value = ConnectionState.Disconnected
                }
                
                override fun onError(ex: Exception) {
                    _connectionState.value = ConnectionState.Error(ex.message ?: "Unknown error")
                }
            }
            
            webSocket?.connect()
            
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.Error(e.message ?: "Connection failed")
        }
    }
    
    fun disconnect() {
        webSocket?.close()
        webSocket = null
        _connectionState.value = ConnectionState.Disconnected
    }
    
    fun sendMessage(content: String, replyTo: String? = null) {
        val message = JsonObject()
        message.addProperty("id", generateId())
        message.addProperty("type", "message")
        message.addProperty("content", content)
        replyTo?.let { message.addProperty("replyTo", it) }
        webSocket?.send(message.toString())
    }
    
    fun sendToolRequest(tool: String, params: Map<String, Any>) {
        val request = JsonObject()
        request.addProperty("id", generateId())
        request.addProperty("type", "tool")
        request.addProperty("tool", tool)
        request.add("params", gson.toJsonTree(params))
        webSocket?.send(request.toString())
    }
    
    private fun handleMessage(message: String) {
        try {
            val json = gson.fromJson(message, JsonObject::class.java)
            val msgType = json.get("type")?.asString ?: return
            
            if (msgType == "message") {
                val msg = Message(
                    id = json.get("id")?.asString ?: generateId(),
                    type = msgType,
                    content = json.get("content")?.asString ?: "",
                    timestamp = json.get("timestamp")?.asLong ?: System.currentTimeMillis()
                )
                _messages.tryEmit(msg)
            } else if (msgType == "response" || msgType == "tool_response") {
                val response = Response(
                    id = json.get("id")?.asString ?: "",
                    type = msgType,
                    data = json
                )
                _responses.tryEmit(response)
            }
        } catch (e: Exception) {
            // Handle parse error
        }
    }
    
    private fun generateId(): String = java.util.UUID.randomUUID().toString()
}
