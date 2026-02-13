package com.ayax.iafront.data

import android.content.Context
import com.ayax.iafront.ChatMessage
import com.ayax.iafront.ChatRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

// Full conversation representation persisted locally as JSON.
data class StoredConversation(
    val id: String,
    val title: String,
    val messages: List<ChatMessage>,
    val updatedAt: Long
)

/**
 * Handles local chat history persistence.
 * Data is stored in app-private files as a single JSON document.
 */
class ChatHistoryStore(context: Context) {

    private val file = File(context.filesDir, "chat_history.json")

    // Loads all saved conversations, returning an empty list if no file exists.
    suspend fun loadAll(): List<StoredConversation> = withContext(Dispatchers.IO) {
        if (!file.exists()) return@withContext emptyList()
        val raw = file.readText(Charsets.UTF_8)
        if (raw.isBlank()) return@withContext emptyList()

        val root = JSONObject(raw)
        val conversations = root.optJSONArray("conversations") ?: JSONArray()
        buildList {
            for (i in 0 until conversations.length()) {
                val item = conversations.optJSONObject(i) ?: continue
                val id = item.optString("id")
                if (id.isBlank()) continue
                val title = item.optString("title", "Conversacion")
                val updatedAt = item.optLong("updatedAt", 0L)
                val messagesJson = item.optJSONArray("messages") ?: JSONArray()
                val messages = buildList {
                    for (m in 0 until messagesJson.length()) {
                        val msg = messagesJson.optJSONObject(m) ?: continue
                        val role = when (msg.optString("role", "assistant")) {
                            "user" -> ChatRole.User
                            else -> ChatRole.Assistant
                        }
                        val content = msg.optString("content")
                        add(ChatMessage(role = role, content = content))
                    }
                }
                add(
                    StoredConversation(
                        id = id,
                        title = title,
                        messages = messages,
                        updatedAt = updatedAt
                    )
                )
            }
        }
    }

    // Rewrites the full history file with the latest in-memory conversations.
    suspend fun saveAll(conversations: List<StoredConversation>) = withContext(Dispatchers.IO) {
        val root = JSONObject()
        val arr = JSONArray()
        conversations.forEach { conv ->
            val messages = JSONArray()
            conv.messages.forEach { message ->
                messages.put(
                    JSONObject()
                        .put("role", if (message.role == ChatRole.User) "user" else "assistant")
                        .put("content", message.content)
                )
            }
            arr.put(
                JSONObject()
                    .put("id", conv.id)
                    .put("title", conv.title)
                    .put("updatedAt", conv.updatedAt)
                    .put("messages", messages)
            )
        }
        root.put("conversations", arr)
        file.writeText(root.toString(), Charsets.UTF_8)
    }
}
