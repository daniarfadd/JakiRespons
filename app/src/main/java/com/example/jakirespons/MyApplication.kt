package com.example.jakirespons

import android.app.Application
import android.content.Context
import com.example.jakirespons.di.appModule
import com.example.jakirespons.di.networkModule
import com.example.jakirespons.utils.Location
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    val location: Location by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(networkModule, appModule))
        }
        instance = this

    }

    companion object {

        lateinit var instance: MyApplication

        fun getContext(): Context = instance.applicationContext
    }

}