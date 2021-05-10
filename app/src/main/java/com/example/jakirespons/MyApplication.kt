package com.example.jakirespons

import android.app.Application
import com.oazisn.moviecatalog.di.appModule
import com.oazisn.moviecatalog.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(networkModule, appModule ))
        }
    }

}