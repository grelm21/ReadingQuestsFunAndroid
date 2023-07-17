package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource
import com.example.readingquestsfun.utils.SharedPreference

class CurrentReadingRepo(private val _prefs: SharedPreference, private val _api: IReadingQuestsApi): BaseRepo() {

    fun getAdminRights(): Boolean{
        return _prefs.getAdminRightFromPref()
    }

    fun getUser(): String?{
        return _prefs.getUserFromPrefs()
    }

    suspend fun getAllStories(): Resource<List<StoryModel>>{
        return apiResponse {
            _api.getAllStories()
        }
    }
}