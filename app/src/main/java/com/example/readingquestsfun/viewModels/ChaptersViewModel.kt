package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.ChaptersRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class ChaptersViewModel(private val _repo: ChaptersRepo, private val _storyId: String) :
    ViewModel() {

    private val _storyModel = MutableLiveData<Resource<StoryModel>>()
    val storyModel = _storyModel

    private val _chapters = MutableLiveData<Resource<List<ChapterModel>>>()
    val chapters = _chapters

    private val _itemsList = MutableLiveData<Resource<List<ItemModel>>>()
    val itemList = _itemsList

    init {
        getStory()
        getStoryChapters()
        getStoryItems()
    }

    private fun getStory() = viewModelScope.launch {
        Log.i("STORY_ID", _storyId!!)
        _storyModel.postValue(Resource.Loading())
        storyModel.postValue(_repo.getStory(_storyId))
    }

    private fun getStoryChapters() = viewModelScope.launch {
        _chapters.postValue(Resource.Loading())
        _chapters.postValue(_repo.getStoryChapters(_storyId))
    }

    fun getStoryItems() = viewModelScope.launch {
        _itemsList.postValue(_repo.getStoryItems(_storyId))
    }

    fun chapterDemo(chapterId: String) = viewModelScope.launch {
        chapterId?.let { _repo.chapterDemo(it) }
    }
}