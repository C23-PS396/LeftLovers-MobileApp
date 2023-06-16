package com.example.LeftLoversApp.local

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("data")
	val data: List<RecommendationItem>
)

data class Location(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("fullLocation")
	val fullLocation: String,

	@field:SerializedName("district")
	val district: String,

	@field:SerializedName("regency")
	val regency: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("village")
	val village: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Seller(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("roleId")
	val roleId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("username")
	val username: String
)

data class Avg(

	@field:SerializedName("rating")
	val rating: Double
)

data class RecommendationItem(

	@field:SerializedName("profilePictureUrl")
	val profilePictureUrl: String,

	@field:SerializedName("seller")
	val seller: Seller,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("sellerId")
	val sellerId: String,

	@field:SerializedName("locationId")
	val locationId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Rating,

	@field:SerializedName("location")
	val location: Location,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Count(

	@field:SerializedName("rating")
	val rating: Int
)

data class Rating(

	@field:SerializedName("_count")
	val count: Count,

	@field:SerializedName("merchantId")
	val merchantId: String,

	@field:SerializedName("_avg")
	val avg: Avg
)
