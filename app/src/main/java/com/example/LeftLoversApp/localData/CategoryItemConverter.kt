package com.example.LeftLoversApp.localData

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.LeftLoversApp.model.CategoryItem

class CategoryItemConverter {
    @TypeConverter
    fun fromCategoryItemList(categoryItems: List<CategoryItem>?): String? {
        if (categoryItems == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(categoryItems)
    }

    @TypeConverter
    fun toCategoryItemList(categoryItemsJson: String?): List<CategoryItem>? {
        if (categoryItemsJson == null) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<CategoryItem>>() {}.type
        return gson.fromJson(categoryItemsJson, listType)
    }
}
