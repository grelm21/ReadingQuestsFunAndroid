package com.example.readingquestsfun

import android.app.Application
import com.example.readingquestsfun.koin.repoModule
import com.example.readingquestsfun.koin.viewModelModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                repoModule,
                viewModelModule
            )
        }
    }
}