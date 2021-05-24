package com.example.jakirespons.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {
    val getApiService: Retrofit by lazy {
        val mLoggingInterceptor = HttpLoggingInterceptor()
        mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val mClient = OkHttpClient.Builder()
            .addInterceptor(mLoggingInterceptor)
            .build()

        return@lazy Retrofit.Builder()
            .baseUrl("https://deft-haven-312422.et.r.appspot.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(mClient)
            .build()
    }

}