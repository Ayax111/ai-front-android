package com.ayax.iafront.ai

/**
 * Contract for model providers used by the chat app.
 * Implementations can target local APIs, embedded runtimes, or mocks.
 */
interface LocalModelEngine {
    suspend fun initialize(): Boolean
    suspend fun listModels(): List<String>
    fun selectModel(modelId: String)
    fun selectedModel(): String?
    fun setBaseUrl(baseUrl: String)
    fun getBaseUrl(): String
    suspend fun generateConversationTitle(firstUserPrompt: String): String
    suspend fun generateReply(prompt: String): String
}
