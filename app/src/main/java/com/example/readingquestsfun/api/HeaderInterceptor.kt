package com.example.readingquestsfun.api

import com.example.readingquestsfun.utils.SharedPreference
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val _pref: SharedPreference): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request?.newBuilder()
            ?.addHeader("Authorization", "Bearer ${_pref.getTokenFromPrefs()}")
            ?.build()

        return chain.proceed(request)
    }
}