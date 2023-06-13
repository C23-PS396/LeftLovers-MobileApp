package com.example.LeftLoversApp.localData

import androidx.room.TypeConverter
import com.example.LeftLoversApp.model.ActiveFood
import com.google.gson.Gson

class ActiveFoodConverter {
    @TypeConverter
    fun fromActiveFood(activeFood: ActiveFood): String {
        val gson = Gson()
        return gson.toJson(activeFood)
    }

    @TypeConverter
    fun toActiveFood(activeFoodJson: String): ActiveFood {
        val gson = Gson()
        return gson.fromJson(activeFoodJson, ActiveFood::class.java)
    }
}
