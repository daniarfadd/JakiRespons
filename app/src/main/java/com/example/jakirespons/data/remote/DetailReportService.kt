package com.oazisn.moviecatalog.data.remote

import com.example.jakirespons.data.remote.response.DetailReportResponseItem
import com.example.jakirespons.data.remote.response.ListReportResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


interface DetailReportService {

    @GET("detail/{id}")
    suspend fun getDetail(@Path("id") id: String): List<DetailReportResponseItem>?

}