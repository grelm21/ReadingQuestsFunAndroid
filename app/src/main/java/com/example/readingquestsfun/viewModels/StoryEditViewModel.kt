package com.example.readingquestsfun.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.StoryEditRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class StoryEditViewModel(private val _repo: StoryEditRepo): ViewModel() {

    private val _storyModelDraft = MutableLiveData<Resource<StoryModel>>()
    val storyModelDraft = _storyModelDraft

    private val _storyModel = MutableLiveData<StoryModel>()
    val storyModel = _storyModel

    private val _deleteStory = MutableLiveData<Resource<ResponseBody>>()
    val deleteStory = _deleteStory

    private val _editedStoryModel = MutableLiveData<Resource<StoryModel>>()
    val editedStoryModel = _editedStoryModel

    fun getViewModel(){

    }

    fun getStory(id: String) = viewModelScope.launch {
        _storyModelDraft.postValue(Resource.Loading())
        val result = _repo.getStory(id)
        _storyModelDraft.postValue(result)
    }

    fun editStory(id: String, title: String, description: String) = viewModelScope.launch {
        val result = _repo.editStory(id, title, description)
        _editedStoryModel.postValue(result)
        _storyModel.postValue(result.data!!)
    }

    fun addNewStory(title: String, descr: String) = viewModelScope.launch {
        _storyModelDraft.postValue(Resource.Loading())
        val result = _repo.addNewStory(title, descr)
        _storyModelDraft.postValue(result)
        _storyModel.postValue(result.data!!)
    }

    fun publish(id: String, publish: Boolean) = viewModelScope.launch {
        _repo.publish(id, publish)
    }

    fun delete(id: String) = viewModelScope.launch {
        _deleteStory.postValue(Resource.Loading())
        _deleteStory.postValue(_repo.delete(id))
    }
}