package com.example.tiendavideojuegos

import android.content.Context

object Prefs {
    private const val PREFS_NAME = "tienda_prefs"
    private const val KEY_USERS = "users" // stored as simple email|password;...
    private const val KEY_LOGGED = "logged_email"

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(context: Context, email: String, password: String) {
        val prefs = getPrefs(context)
        val users = prefs.getString(KEY_USERS, "") ?: ""
        if (!users.contains(email + "|")) {
            prefs.edit().putString(KEY_USERS, users + email + "|" + password + ";").apply()
        }
    }

    fun checkCredentials(context: Context, email: String, password: String): Boolean {
        val users = getPrefs(context).getString(KEY_USERS, "") ?: ""
        val entries = users.split(";").filter { it.isNotBlank() }
        for (e in entries) {
            val parts = e.split("|")
            if (parts.size == 2) {
                if (parts[0] == email && parts[1] == password) return true
            }
        }
        return false
    }

    fun ensurePrecreated(context: Context) {
        val prefs = getPrefs(context)
        val users = prefs.getString(KEY_USERS, "") ?: ""
        if (users.isEmpty()) {
            val seed = "mario@nintendo.com|12345;zelda@hyrule.com|98765;elden@ring.com|00000;"
            prefs.edit().putString(KEY_USERS, seed).apply()
        }
    }

    fun setLogged(context: Context, email: String?) {
        getPrefs(context).edit().putString(KEY_LOGGED, email).apply()
    }

    fun getLogged(context: Context): String? {
        return getPrefs(context).getString(KEY_LOGGED, null)
    }

    fun clearAll(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}
