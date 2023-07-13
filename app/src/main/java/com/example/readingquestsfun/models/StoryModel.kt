package com.example.readingquestsfun.models

import com.google.gson.annotations.SerializedName


data class StoryModel(
    val _id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("chapterCount")
    val chapterCount: Int,
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("published")
    val published: Boolean
)