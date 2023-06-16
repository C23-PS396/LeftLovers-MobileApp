package com.example.LeftLoversApp.local

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("merchantId")
	val merchantId: String,

	@field:SerializedName("totalprice")
	val totalprice: Int,

	@field:SerializedName("review")
	val review: Review,

	@field:SerializedName("customerId")
	val customerId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("status")
	val status: Int
)

data class Review(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("merchantId")
	val merchantId: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("customerId")
	val customerId: String,

	@field:SerializedName("isFilled")
	val isFilled: Boolean,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("transactionId")
	val transactionId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
