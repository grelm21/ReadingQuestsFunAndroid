package com.example.readingquestsfun.utils

import android.content.SharedPreferences
import android.util.Log
import com.example.readingquestsfun.utils.SharedPreference.Constants.ADMIN_KEY
import com.example.readingquestsfun.utils.SharedPreference.Constants.TOKEN_KEY
import com.example.readingquestsfun.utils.SharedPreference.Constants.USER_KEY

class SharedPreference(private val _pref: SharedPreferences) {

    fun addUserToPref(role: String, user: String, token: String) {
        _pref.edit().putString(ADMIN_KEY, role).apply()
        _pref.edit().putString(USER_KEY, user).apply()
        _pref.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getAdminRightFromPref(): String? {
        return _pref.getString(ADMIN_KEY, null)
    }

    fun getUserFromPrefs(): String? {
        return _pref.getString(USER_KEY, null)
    }

    fun getTokenFromPrefs(): String? {
        return _pref.getString(TOKEN_KEY, null)
    }

    object Constants {
        const val PREFERENCE_KEY = "shared_preference"
        const val ADMIN_KEY = "admin_key"
        const val USER_KEY = "user_key"
        const val TOKEN_KEY = "token_key"
    }
}