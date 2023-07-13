package com.example.readingquestsfun.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorMessage: String, code: Int? = null) : Resource<T>(message = errorMessage, code = code)
    class Loading<T> : Resource<T>()
}