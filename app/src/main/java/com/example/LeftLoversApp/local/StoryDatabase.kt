package com.example.LeftLoversApp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Story::class], version = 1)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun daoStory(): DaoStory


    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? =null
        fun getInstance(context: Context): StoryDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, StoryDatabase::class.java,
                "story_db").build()
        }.also { INSTANCE = it }
    }

}