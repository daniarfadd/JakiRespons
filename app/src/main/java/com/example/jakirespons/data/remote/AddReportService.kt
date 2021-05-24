package com.example.jakirespons.data.remote

import com.example.jakirespons.data.remote.response.AddReportResponse
import com.example.jakirespons.data.remote.response.ListReportResponseItem
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AddReportService {

    @Multipart
    @POST("insert-data")
    suspend fun insertData(
        @Part("title") title: String,
        @Part("category") category: String,
        @Part("longitude") long: Double,
        @Part("latitude") lat: Double,
        @Part file: MultipartBody.Part
    ) : List<AddReportResponse>

}