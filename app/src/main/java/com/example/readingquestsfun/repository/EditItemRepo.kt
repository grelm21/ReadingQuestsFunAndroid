package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.utils.Resource

class EditItemRepo(private val _api: IReadingQuestsApi): BaseRepo() {

    suspend fun addItemToStory(storyId: String, name: String, description: String, max: String, type: String): Resource<ResponseModel>{
        return apiCall {
            _api.addItemToStory(storyId, name, description, max, type)
        }
    }

    suspend fun getStoryItem(itemId: String): Resource<ItemModel>{
        return apiResponse {
            _api.getStoryItem(itemId)
        }
    }

    suspend fun editStoryItem(itemId: String, name: String?, description: String?, max: String?, type: String?): Resource<ResponseModel>{
        return apiCall {
            _api.editStoryItem(itemId, name, description, max, type)
        }
    }
}