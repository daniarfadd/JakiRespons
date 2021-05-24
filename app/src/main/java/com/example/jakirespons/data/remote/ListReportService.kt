package com.example.jakirespons.data.remote

import com.example.jakirespons.data.remote.response.ListReportResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


interface ListReportService {

    @GET("sort/{sort}")
    suspend fun getSort(@Path("sort") sort: String): List<ListReportResponseItem>?

    @GET("search/{query}")
    suspend fun getSearch(@Path("query") query: String): List<ListReportResponseItem>?

}