package com.oazisn.moviecatalog.di

import com.oazisn.moviecatalog.data.remote.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideApiService() }
//    single { provideMovieService(get()) }
}

fun provideApiService(): Retrofit {
    return ApiService.getApiService
}

//fun provideMovieService(retrofit: Retrofit): ReportApiService {
//    return retrofit.create(ReportApiService::class.java)
//}
