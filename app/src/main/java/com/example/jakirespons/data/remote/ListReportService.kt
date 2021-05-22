package com.oazisn.moviecatalog.data.remote

import com.example.jakirespons.data.remote.response.ListReportResponse
import com.example.jakirespons.data.remote.response.ListReportResponseItem
import retrofit2.http.GET


interface ListReportService {

    @GET("sort/latest")
    suspend fun getLatest(): List<ListReportResponseItem>?

}