package com.example.readingquestsfun

import android.app.Application
import com.example.readingquestsfun.koin.jsonModule
import com.example.readingquestsfun.koin.networkModule
import com.example.readingquestsfun.koin.preferencesModule
import com.example.readingquestsfun.koin.repoModule
import com.example.readingquestsfun.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                repoModule,
                viewModelModule,
                networkModule,
                jsonModule,
                preferencesModule
            )
        }
    }
}