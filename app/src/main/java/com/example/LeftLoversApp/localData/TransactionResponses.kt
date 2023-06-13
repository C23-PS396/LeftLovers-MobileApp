package com.example.LeftLoversApp.localData

import com.google.gson.annotations.SerializedName

data class TransactionResponses(

	@field:SerializedName("result")
	val result: List<ResultItem?>,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("merchantId")
	val merchantId: String,

	@field:SerializedName("totalprice")
	val totalprice: Int,

	@field:SerializedName("customerId")
	val customerId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("food")
	val food: List<FoodItem?>,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("status")
	val status: Int,
)

data class FoodItem(

	@field:SerializedName("foodPrice")
	val foodPrice: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("foodName")
	val foodName: String,

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("foodId")
	val foodId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("transactionId")
	val transactionId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,
)

data class ResultItem(

	@field:SerializedName("message")
	val message: String
)
