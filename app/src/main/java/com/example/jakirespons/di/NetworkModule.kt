package com.example.jakirespons.di

import com.example.jakirespons.data.remote.AddReportService
import com.example.jakirespons.data.remote.ApiService
import com.example.jakirespons.data.remote.DetailReportService
import com.example.jakirespons.data.remote.ListReportService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideApiService() }
    single { provideListReportService(get()) }
    single { provideDetailReportService(get()) }
    single { provideAddReportService(get()) }
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

fun provideAddReportService(retrofit: Retrofit): AddReportService {
    return retrofit.create(AddReportService::class.java)
}
