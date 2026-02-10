package com.ayax.iafront.ai

interface LocalModelEngine {
    suspend fun initialize(): Boolean
    suspend fun generateReply(prompt: String): String
}
