package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SharedPreference
import okhttp3.ResponseBody

class StoryEditRepo(private val _api: IReadingQuestsApi, private val _prefs: SharedPreference) : BaseRepo() {

    /**
     * добавляем новую историю
     */
    suspend fun addNewStory(title: String, descr: String): Resource<StoryModel> {
        return apiCall {
            _api.addStory(title, descr, getUser())
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
    suspend fun editStory(id: String, title: String, description: String): Resource<StoryModel>{
        return apiCall { _api.editStory(id, title, description) }
    }

    /**
     * публикуем историю
     */
    suspend fun publish(id: String, publish: Boolean): Resource<ResponseBody>{
        return apiCall {
            _api.publishStory(id, publish)
        }
    }

    /**
     * удаляем историю
     */
    suspend fun delete(id: String): Resource<ResponseBody>{
        return apiCall {
            _api.deleteStory(id)
        }
    }

    /**
     * получаем юзера
     */
    private fun getUser(): String{
        return _prefs.getUserFromPrefs() ?: ""
    }
}