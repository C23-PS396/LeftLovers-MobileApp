package com.example.LeftLoversApp.view

import android.content.Context
import com.example.LeftLoversApp.api.ApiConfig
import com.example.LeftLoversApp.local.StoryDatabase
import com.example.LeftLoversApp.local.MainExecutor

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
}