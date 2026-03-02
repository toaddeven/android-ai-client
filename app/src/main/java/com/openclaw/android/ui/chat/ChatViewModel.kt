package com.openclaw.android.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclaw.android.data.gateway.GatewayConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val gateway: GatewayConnection
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()
    
    val connectionState = gateway.connectionState
    
    init {
        viewModelScope.launch {
            gateway.messages.collect { msg ->
                _messages.update { it + ChatMessage(msg.content, false, msg.timestamp) }
            }
        }
    }
    
    fun onInputChange(text: String) {
        _inputText.value = text
    }
    
    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isNotEmpty()) {
            _messages.update { it + ChatMessage(text, true, System.currentTimeMillis()) }
            gateway.sendMessage(text)
            _inputText.value = ""
        }
    }
    
    fun connect() {
        gateway.connect()
    }
    
    fun disconnect() {
        gateway.disconnect()
    }
    
    data class ChatMessage(
        val content: String,
        val isFromUser: Boolean,
        val timestamp: Long
    )
}
