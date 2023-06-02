package com.example.LeftLoversApp.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: data,

	@field:SerializedName("token")
	val token: String
)

data class data(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("roleId")
	val roleId: String,

	@field:SerializedName("role")
	val role: role
)
data class role(
	val id: String,
	val createdAt: String,
	val updatedAt: String,
	val name: String
)
