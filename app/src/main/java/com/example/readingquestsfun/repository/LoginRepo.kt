package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.UserLogin
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SharedPreference

class LoginRepo(private val api: IReadingQuestsApi, private val _prefs: SharedPreference) : BaseRepo() {

    /**
     * отправляем логин и пароль на сервер
     */
    suspend fun login(login: String, password: String): Resource<List<UserLogin>>{
        return apiResponse { api.login(login, password) }
    }

    /**
     * добавляем юзера в префы (логин, админ\не админ)
     */
    fun addUserToPref(isAdmin: Boolean, user: String){
        _prefs.addUserToPref(isAdmin, user)
    }
}