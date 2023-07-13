package com.example.readingquestsfun.utils

import android.content.SharedPreferences
import com.example.readingquestsfun.utils.SharedPreference.Constants.ADMIN_KEY
import com.example.readingquestsfun.utils.SharedPreference.Constants.USER_KEY

class SharedPreference(private val _pref: SharedPreferences) {

    fun addUserToPref(isAdmin: Boolean, user: String) {
        _pref.edit().putBoolean(ADMIN_KEY, isAdmin).apply()
        _pref.edit().putString(USER_KEY, user).apply()
    }

    fun getAdminRightFromPref(): Boolean {
        return _pref.getBoolean(ADMIN_KEY, false)
    }

    fun getUserFromPrefs(): String? {
        return _pref.getString(USER_KEY, null)
    }

    object Constants {
        const val PREFERENCE_KEY = "shared_preference"
        const val ADMIN_KEY = "admin_key"
        const val USER_KEY = "user_key"
    }
}