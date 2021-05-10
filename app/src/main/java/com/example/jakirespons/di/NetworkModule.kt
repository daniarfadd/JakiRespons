package com.oazisn.moviecatalog.di

import com.oazisn.moviecatalog.data.remote.ApiService
import com.oazisn.moviecatalog.data.remote.MovieApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideApiService() }
    single { provideMovieService(get()) }
}

fun provideApiService(): Retrofit {
    return ApiService.getApiService
}

fun provideMovieService(retrofit: Retrofit): MovieApiService {
    return retrofit.create(MovieApiService::class.java)
}
