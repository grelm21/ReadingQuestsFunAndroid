package com.example.readingquestsfun.repository

import com.example.readingquestsfun.utils.SharedPreference

class CurrentReadingRepo(private val _prefs: SharedPreference) {

    fun getAdminRights(): Boolean{
        return _prefs.getAdminRightFromPref()
    }

    fun getUser(): String?{
        return _prefs.getUserFromPrefs()
    }
}