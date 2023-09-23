package com.example.readingquestsfun.viewModels

import android.transition.Visibility
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.repository.EditChapterRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class EditChapterViewModel(
    private val _repo: EditChapterRepo,
    private val _id: String?,
    private val _editType: String
) :
    ViewModel() {

    private val _chapter = MutableLiveData<Resource<ChapterModel>>()
    val chapter = _chapter

    private val _responseModel = MutableLiveData<Resource<ResponseModel>>()
    val responseModel = _responseModel

    private val _itemsList = MutableLiveData<Resource<List<ItemModel>>>()
    val itemModel = _itemsList

    init {
        if (_editType == "EDIT") _id?.let { getChapter(it) }
    }

    private fun getChapter(id: String) = viewModelScope.launch {
        _chapter.postValue(Resource.Loading())
        _chapter.postValue(_repo.getChapter(id))

        val items = _repo.getStoryItems(id)
        if (items is Resource.Success) {
            Log.i("STORY_ITEMS", items.data.toString())
        }else{
            Log.i("STORY_ITEMS", items.message.toString())
        }
    }

    fun addChapter(note: String, text: String) = viewModelScope.launch {
        _id?.let {
            _responseModel.postValue(Resource.Loading())

            val newChapter = _repo.addChapter(it, note, text)
            _responseModel.postValue(newChapter)

            if (newChapter is Resource.Success) {
                Log.i("NEW_CHAPTER", newChapter.data.toString())
                getChapter(newChapter.data!!._id)
            } else {
                Log.i("NEW_CHAPTER", newChapter.message.toString())
            }
        }
    }
}