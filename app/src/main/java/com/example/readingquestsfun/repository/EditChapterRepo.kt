package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.ResponseBody
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.utils.Resource

class EditChapterRepo(private val _api: IReadingQuestsApi): BaseRepo() {

    suspend fun getChapter(chapterId: String): Resource<ChapterModel>{
        return apiResponse {
            _api.getChapter(chapterId)
        }
    }

    suspend fun addChapter(storyId: String, note: String, text: String): Resource<ResponseModel>{
        return apiCall {
            _api.addChapter(storyId, note, text)
        }
    }

    suspend fun getStoryItems(storyId: String): Resource<List<ItemModel>>{
        return apiResponse {
            _api.getStoryItems(storyId)
        }
    }
}