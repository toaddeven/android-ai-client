package com.openclaw.android.data.gateway

import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GatewayConnection @Inject constructor(
    private val gson: Gson
) {
    private var webSocket: WebSocket? = null
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
            
            val client = object : WebSocketClient(uri) {
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
            
            webSocket = client
            client.connect()
            
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
        val message = JsonObject().apply {
            addProperty("id", generateId())
            addProperty("type", "message")
            addProperty("content", content)
            replyTo?.let { addProperty("replyTo", it) }
        }
        webSocket?.send(gson.toJson(message))
    }
    
    fun sendToolRequest(tool: String, params: Map<String, Any>) {
        val request = JsonObject().apply {
            addProperty("id", generateId())
            addProperty("type", "tool")
            addProperty("tool", tool)
            add("params", gson.toJsonTree(params))
        }
        webSocket?.send(gson.toJson(request))
    }
    
    private fun handleMessage(message: String) {
        try {
            val json = gson.fromJson(message, JsonObject::class.java)
            val type = json.get("type")?.asString ?: return
            
            when (type) {
                "message" -> {
                    val msg = Message(
                        id = json.get("id")?.asString ?: generateId(),
                        content = json.get("content")?.asString ?: "",
                        timestamp = json.get("timestamp")?.asLong ?: System.currentTimeMillis()
                    )
                    _messages.tryEmit(msg)
                }
                "response", "tool_response" -> {
                    val response = Response(
                        id = json.get("id")?.asString ?: "",
                        type = type,
                        data = json
                    )
                    _responses.tryEmit(response)
                }
            }
        } catch (e: Exception) {
            // Handle parse error
        }
    }
    
    private fun generateId(): String = java.util.UUID.randomUUID().toString()
}
