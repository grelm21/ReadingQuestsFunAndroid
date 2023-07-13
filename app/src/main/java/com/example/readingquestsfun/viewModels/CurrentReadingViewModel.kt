package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.readingquestsfun.repository.CurrentReadingRepo

class CurrentReadingViewModel(private val _repo: CurrentReadingRepo): ViewModel() {

    fun getAdminRights(): Boolean{
        return _repo.getAdminRights()
    }

    fun getUser(): String{
        return _repo.getUser() ?: "юзер"
    }
}