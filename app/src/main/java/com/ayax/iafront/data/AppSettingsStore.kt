package com.ayax.iafront.data

import android.content.Context

// Small wrapper around SharedPreferences for app-level settings.
class AppSettingsStore(context: Context) {

    private val prefs = context.getSharedPreferences("ia_front_settings", Context.MODE_PRIVATE)

    fun getServerBaseUrl(): String =
        prefs.getString(KEY_SERVER_BASE_URL, DEFAULT_SERVER_BASE_URL) ?: DEFAULT_SERVER_BASE_URL

    fun setServerBaseUrl(value: String) {
        prefs.edit().putString(KEY_SERVER_BASE_URL, value).apply()
    }

    companion object {
        private const val KEY_SERVER_BASE_URL = "server_base_url"
        private const val DEFAULT_SERVER_BASE_URL = "http://192.168.0.194:1234"
    }
}
