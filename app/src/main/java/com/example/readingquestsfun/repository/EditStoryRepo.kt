package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource

class EditStoryRepo(private val _api: IReadingQuestsApi): BaseRepo() {

    /**
     * добавляем новую историю
     */
    suspend fun addNewStory(title: String, descr: String): Resource<ResponseModel> {
        return apiCall {
            _api.addStory(title, descr, "Name Namovich")
        }
    }

    /**
     * получаем историю
     */
    suspend fun getStory(id: String): Resource<StoryModel> {
        return apiResponse { _api.getStory(id) }
    }

    /**
     * редактируем историю
     */
    suspend fun editStory(id: String, title: String, description: String): Resource<ResponseModel> {
        return apiCall { _api.editStory(id, title, description) }
    }
}