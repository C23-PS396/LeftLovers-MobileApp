package com.example.LeftLoversApp.model

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<HistoryItem?>? = null,
//
//	@field:SerializedName("transaction")
//	val transaction: String? = null
)

data class HistoryItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("merchantId")
	val merchantId: String? = null,

	@field:SerializedName("totalprice")
	val totalprice: Int? = null,

	@field:SerializedName("review")
	val review: Any? = null,

	@field:SerializedName("customerId")
	val customerId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("food")
	val food: List<FoodItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("customer")
	val customer: Customer? = null
)

data class Customer(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

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

data class Review(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("merchantId")
	val merchantId: String? = null,

	@field:SerializedName("review")
	val review: Any? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("customerId")
	val customerId: String? = null,

	@field:SerializedName("isFilled")
	val isFilled: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("transactionId")
	val transactionId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class FoodItem(

	@field:SerializedName("foodPrice")
	val foodPrice: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("foodId")
	val foodId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("transactionId")
	val transactionId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
