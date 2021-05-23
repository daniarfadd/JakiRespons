package com.example.jakirespons.data.remote.response

import com.squareup.moshi.Json

data class CurrentStatusItem(

	@field:Json(name="created_at")
	val createdAt: String? = null,

	@field:Json(name="status")
	val status: String? = null,

	@field:Json(name="who")
	val who: String? = null
)