package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.ReadingModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource

class ReaderRepo(private val _api: IReadingQuestsApi): BaseRepo() {

    suspend fun getStory(id: String): Resource<StoryModel> {
        return apiResponse{ _api.getStory(id) }
    }

    suspend fun getChapter(id: String): Resource<ChapterModel> {
        return apiResponse {
            _api.getChapter(id)
        }
    }

    suspend fun getUserItems (id: String): Resource<List<ItemModel>> {
        return apiResponse {
            _api.getUserItems(id)
        }
    }

    suspend fun addItemToUser (storyId: String, itemId: String, quantity: Int): Resource<ReadingModel> {
        return apiCall {
            _api.addItemToUser(storyId, itemId, quantity)
        }
    }
}