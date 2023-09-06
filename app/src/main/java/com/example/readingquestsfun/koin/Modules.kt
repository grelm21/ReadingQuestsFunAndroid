package com.example.readingquestsfun.koin

import android.app.Application
import android.content.SharedPreferences
import com.example.readingquestsfun.api.HeaderInterceptor
import com.example.readingquestsfun.api.IReadingQuestsApi
import com.example.readingquestsfun.api.IReadingQuestsApi.Companion.BASE_URL
import com.example.readingquestsfun.repository.CurrentReadingRepo
import com.example.readingquestsfun.repository.LoginRepo
import com.example.readingquestsfun.repository.ReaderRepo
import com.example.readingquestsfun.repository.StoryEditRepo
import com.example.readingquestsfun.utils.SharedPreference
import com.example.readingquestsfun.utils.SharedPreference.Constants.PREFERENCE_KEY
import com.example.readingquestsfun.viewModels.CurrentReadingViewModel
import com.example.readingquestsfun.viewModels.LoginViewModel
import com.example.readingquestsfun.viewModels.ReaderViewModel
import com.example.readingquestsfun.viewModels.StoryEditViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val jsonModule = module {
//    single {
//        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//    }
}

val networkModule = module {

    single {
        OkHttpClient.Builder().addInterceptor(HeaderInterceptor(get())).build()
    }

    factory {
        Retrofit.Builder()
            .client(get())//добавляет интерсептор хедера для авторизации
//            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()
    }

    single {
        get<Retrofit>().create(IReadingQuestsApi::class.java)
    }
}

val repoModule = module {
    single { LoginRepo(get(), get()) }
    single { StoryEditRepo(get(), get()) }
    single { CurrentReadingRepo(get(), get()) }
    single { ReaderRepo(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { StoryEditViewModel(get()) }
    viewModel { CurrentReadingViewModel(get()) }
    viewModel { ReaderViewModel(get()) }
}

val preferencesModule = module {

    single {
        getSharedPrefs(androidApplication())
    }

    single { SharedPreference(get()) }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        PREFERENCE_KEY, android.content.Context.MODE_PRIVATE
    )
}