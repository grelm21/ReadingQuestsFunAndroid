package com.example.readingquestsfun.repository

import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.LootModel
import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.utils.Resource
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query

class EditChapterRepo(private val _api: IReadingQuestsApi) : BaseRepo() {

    suspend fun getChapters(storyId: String): Resource<List<ChapterModel>> {
        return apiResponse {
            _api.getChapters(storyId)
        }
    }

    suspend fun getChapter(chapterId: String): Resource<ChapterModel> {
        return apiResponse {
            _api.getChapter(chapterId)
        }
    }

    suspend fun addChapter(storyId: String, note: String, text: String): Resource<ResponseModel> {
        return apiCall {
            _api.addChapter(storyId, note, text)
        }
    }

    suspend fun getStoryItems(storyId: String): Resource<List<ItemModel>> {
        return apiResponse {
            _api.getStoryItems(storyId)
        }
    }

    suspend fun addCondition(
        chapterId: String,
        itemId: String,
        checkType: String,
        quantity: String,
        text: String,
        ignoreChapterId: String
    ): Resource<ResponseModel> {
        return apiCall {
            _api.addCondition(chapterId, itemId, checkType, quantity, text, ignoreChapterId)
        }
    }

    suspend fun deleteCondition(chapterId: String): Resource<ResponseModel> {
        return apiCall {
            _api.deleteCondition(chapterId)
        }
    }

    suspend fun editCondition(
        chapterId: String,
        itemId: String,
        checkType: String?,
        quantity: String?,
        text: String?,
        ignoreChapterId: String?
    ): Resource<ResponseModel> {
        return apiCall {
            _api.editCondition(
                chapterId,
                itemId,
                checkType,
                quantity,
                text,
                ignoreChapterId
            )
        }
    }

    suspend fun addLoot(chapterId: String, itemId: String, text: String, quantity: String): Resource<ResponseModel>{
        return apiCall {
            _api.addLoot(chapterId, itemId, text, quantity)
        }
    }

    suspend fun getLoot(chapterId: String, lootId: String): Resource<LootModel>{
        return apiResponse {
            _api.getLoot(chapterId, lootId)
        }
    }

    suspend fun editLoot(chapterId: String, lootId: String, itemId: String, quantity: String?, text: String?): Resource<ResponseModel>{
        return apiCall {
            _api.editLoot(chapterId,lootId, itemId, quantity, text)
        }
    }

    suspend fun chapterDemo(chapterId: String): Resource<ResponseModel>{
        return apiCall {
            _api.chapterDemo(chapterId)
        }
    }

    suspend fun deleteLoot(chapterId: String, lootId: String): Resource<ResponseModel>{
        return apiCall {
            _api.deleteLoot(chapterId, lootId)
        }
    }

    suspend fun updateChapterText(chapterId: String, text: String): Resource<ResponseModel>{
        return apiCall {
            _api.updateChapterText(chapterId, text)
        }
    }

    suspend fun updateChapterNote(chapterId: String, note: String): Resource<ResponseModel>{
        return apiCall {
            _api.updateChapterNote(chapterId, note)
        }
    }
}