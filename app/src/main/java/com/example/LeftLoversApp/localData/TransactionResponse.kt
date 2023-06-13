package com.example.LeftLoversApp.localData

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("foods")
	val foods: List<FoodsItem>,

	@field:SerializedName("merchantId")
	val merchantId: String,

	@field:SerializedName("customerId")
	val customerId: String
)

data class FoodsItem(

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("foodId")
	val foodId: String
)
