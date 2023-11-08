package com.example.readingquestsfun.models

data class ItemModel(
    val _id: String,
    val story_id: String,
    val name: String,
    val description: String,
    val quantity: Int,
    val max: Int,
    val type: String
){
    override fun toString(): String {
        return name
    }
}