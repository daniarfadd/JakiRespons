package com.example.jakirespons.data.remote.response

import com.squareup.moshi.Json

data class CommentsItem(

	@field:Json(name="created_at")
	val createdAt: String? = null,

	@field:Json(name="username")
	val username: String? = null,

	@field:Json(name="discuss")
	val discuss: String? = null
)