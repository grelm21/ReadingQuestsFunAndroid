package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.ResponseModel
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

    suspend fun getStoryItems(storyId: String): Resource<List<ItemModel>> {
        return apiResponse {
            _api.getStoryItems(storyId)
        }
    }

    suspend fun chapterDemo(chapterId: String): Resource<ResponseModel>{
        return apiCall {
            _api.chapterDemo(chapterId)
        }
    }
}