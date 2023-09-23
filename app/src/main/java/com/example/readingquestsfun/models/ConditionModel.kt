package com.example.readingquestsfun.models

class ConditionModel(
    val _id: String,
    val item_id: String,
    val text: String,
    val check: String,
    val ignore_chapter_id: String,
    val item: ItemModel
)