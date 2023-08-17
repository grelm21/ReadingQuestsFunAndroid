package com.example.readingquestsfun.api

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
    @GET("api/user/login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<List<UserLogin>>

    /**
     * получаем историю
     */
    @GET("api/story/get-story/{id}")
    suspend fun getStory(@Path("id") id: String): Response<StoryModel>

    /**
     * добавляем историю (название, автор, описание)
     */
    @FormUrlEncoded
    @POST("api/story/add-story/")
    fun addStory(
        @Field("title") name: String,
        @Field("description") description: String,
        @Field("author") author: String,
        @Field("chapterCount") chapterCount: Int = 0
    ): Call<StoryModel>

    /**
     * редактируем историю (название, автор, описание)
     */
    @FormUrlEncoded
    @POST("api/story/edit/{id}")
    fun editStory (@Path("id") id: String, @Field("title") title: String, @Field ("description") description: String): Call<StoryModel>

    /**
     * публикуем историю для всех
     */
    @POST("api/story/publish/{id}")
    fun publishStory(@Path("id") id: String, @Query("publish") publish: Boolean): Call<ResponseBody>

    /**
     * удаляем историю
     */
    @DELETE("api/story/delete/{id}")
    fun deleteStory(@Path("id") id: String): Call<ResponseBody>

    /**
     * получаем все истории
     */
    @GET("api/story/get-all")
    suspend fun getAllStories(): Response<List<StoryModel>>

    companion object {
        const val BASE_URL = "http://readingquests.fun:3015/"
    }
}