package com.example.LeftLoversApp.view

import android.content.Context
import com.example.LeftLoversApp.api.ApiConfig
import com.example.LeftLoversApp.local.StoryDatabase
import com.example.LeftLoversApp.local.MainExecutor
import com.example.LeftLoversApp.localData.FoodDB
import com.example.LeftLoversApp.view.welcome.FoodRepository

object Injection {
    fun provideRepository(): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository.getInstance(apiService)
    }
    fun provideStoryRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val dao = StoryDatabase.getInstance(context).daoStory()
        val exe = MainExecutor()
        return StoryRepository.getInstance(apiService, dao, exe)
    }
    fun provideFoodRepository(context: Context): FoodRepository {
        val apiService = ApiConfig.getApiService()
        val dao = FoodDB.getInstance(context).daoFood()
        val exe = MainExecutor()
        return FoodRepository.getInstance(apiService, dao, exe)
    }
}