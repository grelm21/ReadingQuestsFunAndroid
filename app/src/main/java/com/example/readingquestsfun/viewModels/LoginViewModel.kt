package com.example.readingquestsfun.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.UserLogin
import com.example.readingquestsfun.repository.LoginRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val _repo: LoginRepo): ViewModel() {

    private val _login = MutableLiveData<Resource<List<UserLogin>>>()
    val login = _login

    fun login(login: String, password: String) = viewModelScope.launch {
        _login.postValue(Resource.Loading())

        _login.postValue(_repo.login(login, password))
    }

    fun addUserToPref(isAdmin: Boolean, user: String){
        _repo.addUserToPref(isAdmin, user)
    }
}