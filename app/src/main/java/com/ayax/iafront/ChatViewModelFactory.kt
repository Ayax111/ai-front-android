package com.ayax.iafront

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ayax.iafront.ai.ApiLocalModelEngine
import com.ayax.iafront.data.AppSettingsStore
import com.ayax.iafront.data.ChatHistoryStore

class ChatViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val appContext = context.applicationContext

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            val settingsStore = AppSettingsStore(appContext)
            return ChatViewModel(
                modelEngine = ApiLocalModelEngine(settingsStore.getServerBaseUrl()),
                historyStore = ChatHistoryStore(appContext),
                appSettingsStore = settingsStore
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
