package com.example.LeftLoversApp.localData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DaoFood {
    @Query("SELECT * FROM food_table_v1")
    fun getFood(): LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun postFood(food: List<Food>)

    @Query("DELETE FROM food_table_v1")
    fun deleteFood()

}