package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.ChapterConditionModel
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.LootModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.ReaderRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class ReaderViewModel(private val _repo: ReaderRepo, private val _storyId: String) : ViewModel() {

    private val _story = MutableLiveData<Resource<StoryModel>>()
    val story = _story

    private val _chapter = MutableLiveData<Resource<ChapterModel>>()
    val chapter = _chapter

    private val _conditions = MutableLiveData<List<ChapterConditionModel>>()
    val conditions = _conditions

    private val _userItems = MutableLiveData<Resource<List<ItemModel>>>()
    val userItems = _userItems

    private val _reservedItems = mutableListOf<LootModel>()
    private val _reservedItemsMutable = MutableLiveData<List<LootModel>>()
    val reservedItemsMutable = _reservedItemsMutable

    private val _conditionCheck = MutableLiveData<Boolean>(true)
    val conditionCheck = _conditionCheck

    init {
        getStory()
    }

    private fun getStory() = viewModelScope.launch {
        _story.postValue(Resource.Loading())
        _story.postValue(_repo.getStory(_storyId))
    }

    fun getChapter(id: String) = viewModelScope.launch {
        _chapter.postValue(Resource.Loading())
        _chapter.postValue(_repo.getChapter(id))
    }

    fun getConditions(chapters: List<String>) = viewModelScope.launch {

        val listOfConditions = mutableListOf<ChapterConditionModel>()

        chapters.forEach { chapterId ->
            val chapter = _repo.getChapter(chapterId)
            if (chapter is Resource.Success) {

                chapter.data!!.condition?.let {chapterCondition ->
                    val condition = ChapterConditionModel(chapterId, chapterCondition)
                    listOfConditions.add(condition)
                }
            }
        }
        _conditions.postValue(listOfConditions)
        Log.i("CONDITIONS", listOfConditions.toString())
    }

    fun getUserItems() = viewModelScope.launch {

        _userItems.postValue(Resource.Loading())
        _userItems.postValue(_repo.getUserItems(_storyId))
    }

    fun addItemToUser(itemId: String, quantity: Int) = viewModelScope.launch {

        _repo.addItemToUser(_storyId, itemId, quantity)
        _repo.getUserItems(_storyId)
    }

    fun addReservedItem(reservedItem: LootModel) {
        _reservedItems.add(reservedItem)
    }

    fun getReservedItems() = viewModelScope.launch {
        _reservedItemsMutable.postValue(_reservedItems)
    }

    fun removeReservedItem(index: Int) {
        _reservedItems.removeAt(index)
//        if (_reservedItems.size == 0) {
//            _reservedItems.clear()
//        }
        getReservedItems()
    }

    fun clearReservedItems() {
        _reservedItems.clear()
        getReservedItems()
//        _reservedItemsMutable.postValue(_reservedItems)
    }

    fun clearProgress() = viewModelScope.launch {
        val clear = _repo.clearProgress(_storyId)

        if (clear is Resource.Success) {
            Log.i("CLEAR", clear.data.toString())
        }
    }

    fun checkCondition(condition: ChapterConditionModel) = viewModelScope.launch {

        val userItems = _repo.getUserItems(_storyId)
        val conditionUserItem = userItems.data!!.find { it._id == condition.condition.item_id }

        if (conditionUserItem != null) {
            _conditionCheck.postValue(conditionUserItem.quantity >= condition.condition.item.quantity)
        } else {
            _conditionCheck.postValue(false)
        }
    }
}