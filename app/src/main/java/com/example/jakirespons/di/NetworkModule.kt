package com.example.jakirespons.di

import com.oazisn.moviecatalog.data.remote.ApiService
import com.oazisn.moviecatalog.data.remote.DetailReportService
import com.oazisn.moviecatalog.data.remote.ListReportService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideApiService() }
    single { provideListReportService(get()) }
    single { provideDetailReportService(get()) }
}

fun provideApiService(): Retrofit {
    return ApiService.getApiService
}

fun provideListReportService(retrofit: Retrofit): ListReportService {
    return retrofit.create(ListReportService::class.java)
}

fun provideDetailReportService(retrofit: Retrofit): DetailReportService {
    return retrofit.create(DetailReportService::class.java)
}
