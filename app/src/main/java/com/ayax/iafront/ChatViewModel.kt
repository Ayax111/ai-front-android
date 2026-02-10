package com.ayax.iafront

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayax.iafront.ai.FakeLocalModelEngine
import com.ayax.iafront.ai.LocalModelEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val modelEngine: LocalModelEngine = FakeLocalModelEngine()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        initializeModel()
    }

    fun initializeModel() {
        viewModelScope.launch {
            val ready = modelEngine.initialize()
            _uiState.value = _uiState.value.copy(isModelReady = ready)
        }
    }

    fun sendMessage(userPrompt: String) {
        val current = _uiState.value
        val withUser = current.messages + ChatMessage(ChatRole.User, userPrompt)
        _uiState.value = current.copy(messages = withUser)

        viewModelScope.launch {
            val reply = if (_uiState.value.isModelReady) {
                modelEngine.generateReply(userPrompt)
            } else {
                "Inicializa el modelo local antes de enviar mensajes."
            }
            _uiState.value = _uiState.value.copy(
                messages = _uiState.value.messages + ChatMessage(ChatRole.Assistant, reply)
            )
        }
    }
}
