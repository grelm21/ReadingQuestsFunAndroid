package com.example.readingquestsfun.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.repository.EditItemRepo
import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.launch

class EditItemDialogViewModel(
    private val _repo: EditItemRepo,
    private val _storyId: String,
    private val _itemId: String?
) : ViewModel() {

    private val _storyItem = MutableLiveData<Resource<ItemModel>>()
    val storyItem = _storyItem

    init {
        if(_itemId != null) getStoryItem()
    }

    fun addItemToStory(name: String, description: String, max: String, type: String) =
        viewModelScope.launch {
            _storyId?.let { _repo.addItemToStory(it, name, description, max, type) }
        }

   private fun getStoryItem() = viewModelScope.launch {
        _itemId?.let { _storyItem.postValue(_repo.getStoryItem(it)) }
    }

    fun editStoryItem(name: String?, description: String?, max: String?, type: String?) = viewModelScope.launch{
        _itemId?.let { _repo.editStoryItem(it, name, description, max, type) }
    }
}