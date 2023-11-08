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
import com.example.readingquestsfun.models.LootModel
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.EditChapterRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class EditChapterViewModel(
    private val _repo: EditChapterRepo,
    private val _storyId: String?,
    private var _chapterId: String?,
    private val _editType: String
) :
    ViewModel() {

    private val _chapters = MutableLiveData<Resource<List<ChapterModel>>>()
    val chapters = _chapters

    private val _chapter = MutableLiveData<Resource<ChapterModel>>()
    val chapter = _chapter

    private val _callResponse = MutableLiveData<Resource<ResponseModel>>()
    val callResponse = _callResponse

    private val _itemsList = MutableLiveData<Resource<List<ItemModel>>>()
    val itemList = _itemsList

    private val _lootList = MutableLiveData<Resource<LootModel>>()
    val lootList = _lootList

    init {
        if (_editType == "EDIT") getChapter()
        else getChapters()
    }

    private fun getChapters() = viewModelScope.launch {
        _chapters.postValue(Resource.Loading())
        _storyId?.let { _chapters.postValue(_repo.getChapters(it)) }
    }

    private fun getChapter() = viewModelScope.launch {
        _chapter.postValue(Resource.Loading())
        val chapter = _chapterId?.let { _repo.getChapter(it) }
        _chapter.postValue(chapter!!)

        if (chapter is Resource.Success) {
            getChapters()
            _itemsList.postValue(_storyId?.let { _repo.getStoryItems(it) })
        }
    }

    fun addChapter(note: String, text: String) = viewModelScope.launch {
        _storyId?.let {
            _callResponse.postValue(Resource.Loading())

            val newChapter = _repo.addChapter(it, note, text)
            _callResponse.postValue(newChapter)

            if (newChapter is Resource.Success) {
                _chapterId = newChapter.data!!._id
                getChapter()
            }
        }
    }

    fun addCondition(
        itemId: String,
        checkType: String,
        quantity: String,
        text: String,
        ignoreChapterId: String
    ) = viewModelScope.launch {
        _callResponse.postValue(_chapterId?.let {
            _repo.addCondition(
                it,
                itemId,
                checkType,
                quantity,
                text,
                ignoreChapterId
            )
        })
        getChapter()
    }

    fun deleteCondition() = viewModelScope.launch {
        _chapterId?.let {
            _repo.deleteCondition(it)
            getChapter()
        }
    }

    fun editCondition(
        itemId: String,
        checkType: String?,
        quantity: String?,
        text: String?,
        ignoreChapterId: String?
    ) = viewModelScope.launch {
        _callResponse.postValue(_chapterId?.let {
            _repo.editCondition(
                it,
                itemId,
                checkType,
                quantity,
                text,
                ignoreChapterId
            )
        })
    }

    fun addLoot(itemId: String, text: String, quantity: String) = viewModelScope.launch {
        _chapterId?.let {
            _repo.addLoot(it, itemId, text, quantity)
        }
        getChapter()
    }

    fun getLoot(lootId: String) = viewModelScope.launch {
        _chapterId?.let {
            _lootList.postValue(_repo.getLoot(it, lootId))
        }
    }

    fun editLoot(lootId: String, itemId: String, quantity: String?, text: String?) =
        viewModelScope.launch {
            _chapterId?.let {
                val newLoot = _repo.editLoot(it, lootId, itemId, quantity, text)

                if (newLoot is Resource.Success) {
                    Log.i("NEW_LOOT", newLoot.data.toString())
                }
            }
            getChapter()
        }

    fun chapterDemo() = viewModelScope.launch {
        _callResponse.postValue(_chapterId?.let { _repo.chapterDemo(it) })
    }

    fun deleteLoot(lootId: String) = viewModelScope.launch {
        _callResponse.postValue(_chapterId?.let { _repo.deleteLoot(it, lootId) })

        getChapter()
    }
}