package com.app.elenchos.presentation.repository

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil internal constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun setAppBlocked(packageName: String, isBlocked: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(packageName, isBlocked)
            apply()
        }
    }

    fun isAppBlocked(packageName: String): Boolean {
        return sharedPreferences.getBoolean(packageName, false)
    }

    companion object {
        @Volatile
        private var INSTANCE: SharedPrefUtil? = null

        fun getInstance(context: Context): SharedPrefUtil {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPrefUtil(context).also { INSTANCE = it }
            }
        }
    }
}
