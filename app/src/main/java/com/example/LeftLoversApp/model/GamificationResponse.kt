package com.example.LeftLoversApp.model

import com.google.gson.annotations.SerializedName

data class GamificationResponse(

	@field:SerializedName("GamificationResponse")
	val gamificationResponse: List<GamificationResponseItem?>? = null
)

data class TotalPoint(

	@field:SerializedName("totalprice")
	val totalprice: Int? = null
)

data class GamificationResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("totalPoint")
	val totalPoint: TotalPoint? = null,

	@field:SerializedName("roleId")
	val roleId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("fullname")
	val fullname: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
