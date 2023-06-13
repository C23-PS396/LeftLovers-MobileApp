package com.example.LeftLoversApp.localData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Food::class], version = 1)
abstract class FoodDB : RoomDatabase() {
    abstract fun daoFood(): DaoFood


    companion object {
        @Volatile
        private var INSTANCE: FoodDB? =null
        fun getInstance(context: Context): FoodDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, FoodDB::class.java,
                "food_db_v1").build()
        }.also { INSTANCE = it }
    }

}