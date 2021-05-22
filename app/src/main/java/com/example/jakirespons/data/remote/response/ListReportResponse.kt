package com.example.jakirespons.data.remote.response

import com.squareup.moshi.Json

data class ListReportResponse(

	@field:Json(name="ListReportResponse")
	val listReportResponse: List<ListReportResponseItem>? = null
)