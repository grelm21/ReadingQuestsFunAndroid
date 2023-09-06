package com.example.readingquestsfun.models

data class LootConditionModel(
    val _id: String,
    val item_id: String,
    val text: String,
    val check: String?,
//    val check: String,
    val item: ItemModel
)