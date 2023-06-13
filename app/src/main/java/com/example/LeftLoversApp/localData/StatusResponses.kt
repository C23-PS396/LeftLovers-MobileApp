package com.example.LeftLoversApp.localData

import com.google.gson.annotations.SerializedName

data class StatusResponses(

	@field:SerializedName("data")
	val data: DataStatus,

	@field:SerializedName("message")
	val message: String
)

data class Customer(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("roleId")
	val roleId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("fullname")
	val fullname: Any,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("username")
	val username: String
)

data class Merchant(

	@field:SerializedName("profilePictureUrl")
	val profilePictureUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("sellerId")
	val sellerId: String,

	@field:SerializedName("locationId")
	val locationId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

//data class FoodItem(
//
//	@field:SerializedName("foodPrice")
//	val foodPrice: Int,
//
//	@field:SerializedName("createdAt")
//	val createdAt: String,
//
//	@field:SerializedName("foodName")
//	val foodName: String,
//
//	@field:SerializedName("quantity")
//	val quantity: Int,
//
//	@field:SerializedName("foodId")
//	val foodId: String,
//
//	@field:SerializedName("id")
//	val id: String,
//
//	@field:SerializedName("transactionId")
//	val transactionId: String,
//
//	@field:SerializedName("updatedAt")
//	val updatedAt: String
//)

data class DataStatus(

//	@field:SerializedName("createdAt")
//	val createdAt: String,
//
//	@field:SerializedName("merchantId")
//	val merchantId: String,
//
//	@field:SerializedName("totalprice")
//	val totalprice: Int,
//
//	@field:SerializedName("review")
//	val review: String,
//
//	@field:SerializedName("customerId")
//	val customerId: String,
//
//	@field:SerializedName("merchant")
//	val merchant: Merchant,
	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("id")
	val id: String,

//	@field:SerializedName("food")
//	val food: List<FoodItem>,
//
//	@field:SerializedName("updatedAt")
//	val updatedAt: String,



//	@field:SerializedName("customer")
//	val customer: Customer
)
