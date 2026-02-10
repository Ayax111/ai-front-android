package com.ayax.iafront

enum class ChatRole {
    User,
    Assistant
}

data class ChatMessage(
    val role: ChatRole,
    val content: String
)

data class ChatUiState(
    val isModelReady: Boolean = false,
    val messages: List<ChatMessage> = emptyList()
)
