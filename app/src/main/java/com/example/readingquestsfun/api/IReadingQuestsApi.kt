package com.example.readingquestsfun.api

import com.example.readingquestsfun.models.ResponseModel
import com.example.readingquestsfun.models.ChapterModel
import com.example.readingquestsfun.models.ItemModel
import com.example.readingquestsfun.models.LootModel
import com.example.readingquestsfun.models.ReadingModel
import com.example.readingquestsfun.models.StoryModel
import com.example.readingquestsfun.models.UserLogin
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IReadingQuestsApi {

    /**
     * получаем логин и пароль пользователя
     */
//    @FormUrlEncoded
    @GET("api/auth/login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<UserLogin>

    @FormUrlEncoded
    @POST("api/auth/signup")
    fun signup(
        @Field("login") login: String,
        @Field("password") password: String
    ): Call<UserLogin>

    /**
     * получаем историю
     */
    @GET("api/story/get-story/{id}")
    suspend fun getStory(@Path("id") id: String): Response<StoryModel>

    /**
     * добавляем историю (название, автор, описание)
     */
    @FormUrlEncoded
    @POST("api/admin/add-story")
    fun addStory(
        @Field("title") name: String,
        @Field("description") description: String,
        @Field("author") author: String,
//        @Field("chapterCount") chapterCount: Int = 0
    ): Call<ResponseModel>

    /**
     * редактируем историю (название, автор, описание)
     */
    @FormUrlEncoded
    @POST("api/admin/edit/{id}")
    fun editStory(
        @Path("id") id: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Call<ResponseModel>

    /**
     * публикуем историю для всех
     */
    @POST("api/admin/publish/{id}")
    fun publishStory(@Path("id") id: String, @Query("publish") publish: Boolean): Call<ResponseBody>

    /**
     * удаляем историю
     */
    @DELETE("api/admin/delete/{id}")
    fun deleteStory(@Path("id") id: String): Call<ResponseBody>

    /**
     * получаем все истории
     */
    @GET("api/story/get-all")
    suspend fun getAllStories(): Response<List<StoryModel>>

    @GET("api/story/get-chapter/{id}")
    suspend fun getChapter(@Path("id") id: String): Response<ChapterModel>

    @GET("api/story/get-chapters/{id}")
    suspend fun getChapters(@Path("id") storyId: String): Response<List<ChapterModel>>

    @GET("api/story/get-user-items/{id}")
    suspend fun getUserItems(@Path("id") id: String): Response<List<ItemModel>>

    @POST("api/story/add-item-to-user")
    fun addItemToUser(
        @Query("story_id") storyId: String,
        @Query("item_id") itemId: String,
        @Query("quantity") quantity: Int
    ): Call<ReadingModel>

    @DELETE("api/story/clear-progress/{id}")
    fun clearProgress(@Path("id") storyId: String): Call<ResponseBody>

    @GET("api/story/get-story-chapters/{id}")
    suspend fun getStoryChapters(@Path("id") storyId: String): Response<List<ChapterModel>>

    @FormUrlEncoded
    @POST("api/admin/add-chapter")
    fun addChapter(
        @Query("story_id") id: String,
        @Field("note") note: String,
        @Field("text") text: String
    ): Call<ResponseModel>

    @GET("api/admin/story-items/{id}")
    suspend fun getStoryItems(@Path("id") storyId: String): Response<List<ItemModel>>

    @FormUrlEncoded
    @POST("api/admin/add-condition")
    fun addCondition(
        @Query("chapter_id") chapterId: String,
        @Query("item_id") itemId: String,
        @Field("check_type") checkType: String,
        @Field("quantity") quantity: String,
        @Field("text") text: String,
        @Query("ignore_chapter_id") ignoreChapterId: String
    ): Call<ResponseModel>

    @POST("api/admin/delete-condition/{id}")
    fun deleteCondition(@Path("id") chapterId: String): Call<ResponseModel>

    @FormUrlEncoded
    @POST("api/admin/update-condition/{id}")
    fun editCondition(
        @Path("id") chapterId: String,
        @Field("item_id") itemId: String,
        @Field("check_type") checkType: String?,
        @Field("quantity") quantity: String?,
        @Field("text") text: String?,
        @Field("ignore_chapter_id") ignoreChapterId: String?
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("api/admin/add-loot")
    fun addLoot(
        @Query("chapter_id") chapterId: String,
        @Query("item_id") itemId: String,
        @Field("text") text: String,
        @Field("quantity") quantity: String
    ): Call<ResponseModel>

    @GET("api/admin/get-loot/{chapterId}/{lootId}")
    suspend fun getLoot(
        @Path("chapterId") chapterId: String,
        @Path("lootId") lootId: String
    ): Response<LootModel>

    @FormUrlEncoded
    @POST("api/admin/update-loot/{chapterId}")
    fun editLoot(
        @Path("chapterId") chapterId: String,
        @Field("id") lootId: String,
        @Field("item_id") itemId: String,
        @Field("quantity") quantity: String?,
        @Field("text") text: String?
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("api/admin/add-item-to-story/{storyId}")
    fun addItemToStory(
        @Path("storyId") storyId: String,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("max") max: String,
        @Field("type") type: String
    ): Call<ResponseModel>

    @GET("api/admin/get-story-item/{itemId}")
    suspend fun getStoryItem(
        @Path("itemId") itemId: String
    ): Response<ItemModel>

    @FormUrlEncoded
    @POST("api/admin/update-story-item/{itemId}")
    fun editStoryItem(
        @Path("itemId") itemId: String,
        @Field("name") name: String?,
        @Field("description") description: String?,
        @Field("max") max: String?,
        @Field("type") type: String?
    ): Call<ResponseModel>

    @POST("api/admin/chapter-demo/{chapterId}")
    fun chapterDemo(@Path("chapterId") chapterId: String): Call<ResponseModel>

    @POST("api/admin/delete-loot/{chapterId}/{lootId}")
    fun deleteLoot(
        @Path("chapterId") chapterId: String,
        @Path("lootId") lootId: String
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("api/admin/update-chapter-text/{chapterId}")
    fun updateChapterText(
        @Path("chapterId") chapterId: String,
        @Field("text") text: String
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("api/admin/update-chapter-note/{chapterId}")
    fun updateChapterNote(
        @Path("chapterId") chapterId: String,
        @Field("note") note: String
    ): Call<ResponseModel>

    companion object {
        const val BASE_URL = "http://readingquests.fun:3015/"
        const val ADMIN = "api/admin/"
        const val STORY = "api/story/"
        const val AUTH = "api/auth/"
    }
}