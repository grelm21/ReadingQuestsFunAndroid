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
    val chapter_count: Int,
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("published")
    val published: Boolean,
    @SerializedName("chapters")
    val chapters: List<String>
)