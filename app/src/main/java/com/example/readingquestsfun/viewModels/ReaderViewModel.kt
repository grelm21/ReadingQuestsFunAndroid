package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.ChapterConditionModel
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.LootConditionModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.repository.ReaderRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class ReaderViewModel(private val _repo: ReaderRepo) : ViewModel() {

    private val _story = MutableLiveData<Resource<StoryModel>>()
    val story = _story

    private val _chapter = MutableLiveData<Resource<ChapterModel>>()
    val chapter = _chapter

    private val _conditions = MutableLiveData<List<ChapterConditionModel>>()
    val conditions = _conditions

    private val _userItems = MutableLiveData<Resource<List<ItemModel>>>()
    val userItems = _userItems

    private val _reservedItems = mutableListOf<LootConditionModel>()
    private val _reservedItemsMutable = MutableLiveData<List<LootConditionModel>>()
    val reservedItemsMutable = _reservedItemsMutable

    private val _conditionCheck = MutableLiveData<Boolean>(true)
    val conditionCheck = _conditionCheck

    fun getStory(id: String) = viewModelScope.launch {
        _story.postValue(Resource.Loading())
        _story.postValue(_repo.getStory(id))
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
                val condition = ChapterConditionModel(chapterId, chapter.data!!.condition)
                listOfConditions.add(condition)
            }
        }
        _conditions.postValue(listOfConditions)
        Log.i("CONDITIONS", listOfConditions.toString())
    }

    fun getUserItems(storyId: String) = viewModelScope.launch {

        _userItems.postValue(Resource.Loading())
        _userItems.postValue(_repo.getUserItems(storyId))
    }

    fun addItemToUser(storyId: String, itemId: String, quantity: Int) = viewModelScope.launch {

        _repo.addItemToUser(storyId, itemId, quantity)
        _repo.getUserItems("64f20d1c49ab400e70a414c2")
    }

    fun addReservedItem(reservedItem: LootConditionModel) {
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

    fun checkCondition(condition: ChapterConditionModel, storyId: String) = viewModelScope.launch {

        val userItems = _repo.getUserItems(storyId)
        val conditionUserItem = userItems.data!!.find { it._id == condition.condition.item_id }

        if (conditionUserItem != null) {
            _conditionCheck.postValue(conditionUserItem.quantity >= condition.condition.item.quantity)
        }else{
            _conditionCheck.postValue(false)
        }
    }
}