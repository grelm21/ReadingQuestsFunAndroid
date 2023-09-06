package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SharedPreference
import okhttp3.ResponseBody

class CurrentReadingRepo(private val _prefs: SharedPreference, private val _api: IReadingQuestsApi): BaseRepo() {

    /**
     * получаем бульку на права адим\не админ из префоф
     */
    fun getAdminRights(): String?{
        return _prefs.getAdminRightFromPref()
    }

    /**
     * получаем юзера из префоф
     */
    fun getUser(): String?{
        return _prefs.getUserFromPrefs()
    }

    /**
     * получаем список всех историй
     */
    suspend fun getAllStories(): Resource<List<StoryModel>>{
        return apiResponse {
            _api.getAllStories()
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
}