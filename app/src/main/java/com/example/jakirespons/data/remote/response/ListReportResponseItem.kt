package com.example.jakirespons.data.remote.response

import com.squareup.moshi.Json

data class ListReportResponseItem(

	@field:Json(name="review_star")
	val reviewStar: Any? = null,

	@field:Json(name="created_at")
	val createdAt: String? = null,

	@field:Json(name="photo")
	val photo: String? = null,

	@field:Json(name="id")
	val id: String? = null,

	@field:Json(name="longi")
	val longi: String? = null,

	@field:Json(name="title")
	val title: String? = null,

	@field:Json(name="lat")
	val lat: String? = null,

	@field:Json(name="status")
	val status: String? = null
)