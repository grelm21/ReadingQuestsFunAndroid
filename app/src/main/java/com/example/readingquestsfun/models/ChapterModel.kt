package com.example.readingquestsfun.models

data class ChapterModel(
    val _id: String,
    val story_id: String,
    val next_chapter_id: List<String>,
    val text: String,
    val note: String,
    val demo: Boolean,
    val published: Boolean,
    val level: Int,
    val loot: List<LootConditionModel>,
    val condition: LootConditionModel
)