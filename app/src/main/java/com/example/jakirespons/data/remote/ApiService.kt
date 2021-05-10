package com.oazisn.moviecatalog.data.remote

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

        val apiKeyInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", "38c6ea3db680fae502416d80ff416a0b").build()
                original = original.newBuilder().url(url).build()
                return chain.proceed(original)
            }
        }

        val mClient = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(mLoggingInterceptor)
            .build()

        return@lazy Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(mClient)
            .build()
    }
}