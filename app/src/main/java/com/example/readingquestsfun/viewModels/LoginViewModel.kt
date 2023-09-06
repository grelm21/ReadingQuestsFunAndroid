package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.UserLogin
import com.example.readingquestsfun.repository.LoginRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val _repo: LoginRepo): ViewModel() {

    private val _login = MutableLiveData<Resource<UserLogin>>()
    val login = _login

    fun login(login: String, password: String) = viewModelScope.launch {
        _login.postValue(Resource.Loading())
        val loginResponse = _repo.login(login, password)

        if (loginResponse is Resource.Success){
            _repo.addUserToPref(loginResponse.data!!.user.role, loginResponse.data.user.login, loginResponse.data.token)
        }

        _login.postValue(_repo.login(login, password))
    }

    fun signup(login: String, password: String) = viewModelScope.launch {
        _login.postValue(Resource.Loading())
        val signup = _repo.signup(login, password)

        if (signup is Resource.Success){
            _repo.addUserToPref(signup.data!!.user.role, signup.data.user.login, signup.data.token)
        }
        _login.postValue(signup)
    }

}