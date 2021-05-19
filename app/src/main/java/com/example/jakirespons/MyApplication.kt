package com.example.jakirespons

import android.app.Application
import android.content.Context
import com.example.jakirespons.di.appModule
import com.example.jakirespons.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(networkModule, appModule))
        }
        instance = this

    }

    companion object {

        var foregroundOnlyLocationServiceBound = false

        lateinit var instance: MyApplication

        fun getContext(): Context = instance.applicationContext
    }

}