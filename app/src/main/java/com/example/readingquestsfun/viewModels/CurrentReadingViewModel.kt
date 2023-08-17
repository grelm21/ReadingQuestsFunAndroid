package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.CurrentReadingRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class CurrentReadingViewModel(private val _repo: CurrentReadingRepo): ViewModel() {

    private val _allStories = MutableLiveData<Resource<List<StoryModel>>>()
    val allStories = _allStories

    private val _deleteStory = MutableLiveData<Resource<ResponseBody>>()
    val deleteStory = _deleteStory

    fun getAdminRights(): Boolean{
        return _repo.getAdminRights()
    }

    fun getUser(): String{
        return _repo.getUser() ?: "юзер"
    }

    fun getAllStories() = viewModelScope.launch {
        _allStories.postValue(Resource.Loading())

        _allStories.postValue(_repo.getAllStories())
    }

    fun delete(id: String) = viewModelScope.launch {
        _deleteStory.postValue(Resource.Loading())
        _deleteStory.postValue(_repo.delete(id))
    }
}