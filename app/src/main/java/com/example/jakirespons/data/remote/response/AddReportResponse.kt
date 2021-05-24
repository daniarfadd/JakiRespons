package com.example.jakirespons.data.remote.response

import com.squareup.moshi.Json

data class AddReportResponse(

	@field:Json(name="review_star")
	val reviewStar: Any? = null,

	@field:Json(name="created_at")
	val createdAt: String? = null,

	@field:Json(name="photo")
	val photo: String? = null,

	@field:Json(name="review_text")
	val reviewText: Any? = null,

	@field:Json(name="id")
	val id: Int? = null,

	@field:Json(name="longi")
	val longi: String? = null,

	@field:Json(name="title")
	val title: String? = null,

	@field:Json(name="category")
	val category: String? = null,

	@field:Json(name="support")
	val support: Any? = null,

	@field:Json(name="lat")
	val lat: String? = null,

	@field:Json(name="review_photo")
	val reviewPhoto: Any? = null,

	@field:Json(name="status")
	val status: Any? = null
)