package com.oazisn.moviecatalog.data.remote

import com.example.jakirespons.data.remote.response.ListReportResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


interface AddReportService {

    @GET("sort/{sort}")
    suspend fun getSort(@Path("sort") sort: String): List<ListReportResponseItem>?

    @GET("search/{query}")
    suspend fun getSearch(@Path("query") query: String): List<ListReportResponseItem>?

}