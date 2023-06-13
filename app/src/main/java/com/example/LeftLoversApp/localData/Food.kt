package com.example.LeftLoversApp.localData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.LeftLoversApp.model.ActiveFood
import com.example.LeftLoversApp.model.CategoryItem
import androidx.room.TypeConverters;

@Entity(tableName = "food_table_v1")
@TypeConverters(CategoryItemConverter::class, ActiveFoodConverter::class)
data class Food(
	@field:ColumnInfo(name = "id")
	@PrimaryKey
	val id: String,

	@field:ColumnInfo(name = "createdAt")
	val createdAt: String,

	@field:ColumnInfo(name = "updatedAt")
	val updatedAt: String,

	@field:ColumnInfo(name = "name")
	val name: String,

	@field:ColumnInfo(name = "pictureUrl")
	val pictureUrl: String,

	@field:ColumnInfo(name = "price")
	val price: Double,

	@field:ColumnInfo(name = "merchantId")
	val merchantId: String,

	@field:ColumnInfo(name = "category")
	val category: List<CategoryItem>,

	@field:ColumnInfo(name = "activeFood")
	val activeFood: ActiveFood,

//	@field:ColumnInfo(name = "category")
//	val category: CategoryItem,
//
//	@field:ColumnInfo(name = "activeFood")
//	val activeFood: ActiveFood
)
