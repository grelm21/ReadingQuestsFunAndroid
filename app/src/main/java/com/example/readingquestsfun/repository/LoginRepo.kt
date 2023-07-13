package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.UserLogin
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SharedPreference

class LoginRepo(private val api: IReadingQuestsApi, private val _prefs: SharedPreference) : BaseRepo() {

    suspend fun login(login: String, password: String): Resource<List<UserLogin>>{
        return apiResponse { api.login(login, password) }
    }

    fun addUserToPref(isAdmin: Boolean, user: String){
        _prefs.addUserToPref(isAdmin, user)
    }
}