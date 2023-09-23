package com.example.readingquestsfun.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.EditStoryRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class StoryEditViewModel(private val _repo: EditStoryRepo): ViewModel() {

//    private val _storyModelDraft = MutableLiveData<Resource<StoryModel>>()
//    val storyModelDraft = _storyModelDraft

    private val _storyModel = MutableLiveData<Resource<StoryModel>>()
    val storyModel = _storyModel

//    private val _deleteStory = MutableLiveData<Resource<ResponseBody>>()
//    val deleteStory = _deleteStory

    private val _editedStoryModel = MutableLiveData<Resource<StoryModel>>()
    val editedStoryModel = _editedStoryModel

    private val _response = MutableLiveData<Resource<ResponseModel>>()
    val response = _response

    fun getStory(id: String) = viewModelScope.launch {
        _storyModel.postValue(Resource.Loading())
        storyModel.postValue(_repo.getStory(id))
    }

    fun editStory(id: String, title: String, description: String) = viewModelScope.launch {
        _response.postValue(Resource.Loading())
        _response.postValue(_repo.editStory(id, title, description))
    }

    fun addNewStory(title: String, descr: String) = viewModelScope.launch {
        _response.postValue(Resource.Loading())
        _response.postValue(_repo.addNewStory(title, descr))
    }
}