package com.ayax.iafront.ai

import kotlinx.coroutines.delay

class FakeLocalModelEngine : LocalModelEngine {

    private var initialized = false

    override suspend fun initialize(): Boolean {
        delay(350)
        initialized = true
        return initialized
    }

    override suspend fun generateReply(prompt: String): String {
        delay(250)
        if (!initialized) return "Modelo no inicializado."
        return "[Mock local] Recibido: $prompt"
    }
}
