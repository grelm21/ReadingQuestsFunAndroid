package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.UserLogin
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SharedPreference
import retrofit2.Call
import retrofit2.Response

class LoginRepo(private val api: IReadingQuestsApi, private val _prefs: SharedPreference) : BaseRepo() {

    /**
     * отправляем логин и пароль на сервер
     */
    suspend fun login(login: String, password: String): Resource<UserLogin>{
        return apiResponse { api.login(login, password) }
    }

    suspend fun signup(login: String, password: String): Resource<UserLogin> {
        return apiCall { api.signup(login, password) }
    }

    /**
     * добавляем юзера в префы (логин, админ\не админ)
     */
    fun addUserToPref(role: String, user: String, token: String){
        _prefs.addUserToPref(role, user, token)
    }
}