package com.example.readingquestsfun.models

data class UserLogin(
    val user: UserInfo,
    val token: String
)

data class UserInfo(
    val login: String,
    val role: String,
    val token: String
)

enum class Role {
    READER,
    AUTHOR,
    ADMIN
}
