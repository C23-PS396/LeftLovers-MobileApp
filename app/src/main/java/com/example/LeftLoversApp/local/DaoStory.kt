package com.example.LeftLoversApp.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoStory {
    @Query("SELECT * FROM story_table")
    fun getStory(): LiveData<List<Story>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun postStory(story: List<Story>)

    @Query("DELETE FROM story_table")
    fun deleteStory()

}