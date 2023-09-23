package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource

class ChaptersRepo(private val _api: IReadingQuestsApi): BaseRepo() {

    /**
     * получаем историю
     */
    suspend fun getStory(id: String): Resource<StoryModel> {
        return apiResponse { _api.getStory(id) }
    }

    suspend fun getStoryChapters(id: String): Resource<List<ChapterModel>> {
        return apiResponse { _api.getStoryChapters(id) }
    }
}