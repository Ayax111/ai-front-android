package com.ayax.iafront

// Role associated with each chat message in a conversation.
enum class ChatRole {
    User,
    Assistant
}

// Message object rendered in the chat timeline.
data class ChatMessage(
    val role: ChatRole,
    val content: String
)

// Minimal metadata used in the history list drawer.
data class ConversationSummary(
    val id: String,
    val title: String,
    val updatedAt: Long
)

// UI state observed by Compose.
data class ChatUiState(
    val isModelReady: Boolean = false,
    val serverBaseUrl: String = "http://192.168.0.194:1234",
    val availableModels: List<String> = emptyList(),
    val selectedModel: String? = null,
    val isLoadingModels: Boolean = false,
    val statusMessage: String? = null,
    val history: List<ConversationSummary> = emptyList(),
    val currentConversationId: String? = null,
    val messages: List<ChatMessage> = emptyList()
)
