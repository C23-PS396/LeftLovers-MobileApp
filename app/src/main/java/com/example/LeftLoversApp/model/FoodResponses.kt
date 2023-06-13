package com.example.LeftLoversApp.model

import com.google.gson.annotations.SerializedName

data class FoodResponses(

	@field:SerializedName("data")
	val data: List<DataItem>
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("merchantId")
	val merchantId: String,

	@field:SerializedName("price")
	val price: Double,

	@field:SerializedName("pictureUrl")
	val pictureUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("activeFood")
	val activeFood: ActiveFood,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: List<CategoryItem>,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class CategoryItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class ActiveFood(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("durationInSecond")
	val durationInSecond: Int,

	@field:SerializedName("foodId")
	val foodId: String,

	@field:SerializedName("startTime")
	val startTime: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("endTime")
	val endTime: String,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("isActive")
	val isActive: Boolean,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
