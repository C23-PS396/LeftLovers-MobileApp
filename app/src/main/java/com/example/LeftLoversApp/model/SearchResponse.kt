package com.example.LeftLoversApp.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("data")
	val data: List<SearchItem?>? = null
)

data class Rating(

	@field:SerializedName("_count")
	val count: Count? = null,

	@field:SerializedName("merchantId")
	val merchantId: String? = null,

	@field:SerializedName("_avg")
	val avg: Avg? = null
)

data class SearchItem(

	@field:SerializedName("profilePictureUrl")
	val profilePictureUrl: String? = null,

	@field:SerializedName("seller")
	val seller: Seller? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("sellerId")
	val sellerId: String? = null,

	@field:SerializedName("locationId")
	val locationId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Rating? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Avg(

	@field:SerializedName("rating")
	val rating: Any? = null
)

data class Count(

	@field:SerializedName("rating")
	val rating: Int? = null
)

data class Location(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("fullLocation")
	val fullLocation: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("regency")
	val regency: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Seller(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("roleId")
	val roleId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
