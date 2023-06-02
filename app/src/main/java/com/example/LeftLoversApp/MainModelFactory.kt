package com.example.LeftLoversApp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.StoryRepository

class MainModelFactory private constructor(private val storyRepository: StoryRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(storyRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: MainModelFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference
        ): MainModelFactory = instance ?: synchronized(this) {
            instance ?: MainModelFactory(
                Injection.provideStoryRepository(context),
                userPreference
            )
        }.also { instance = it }
    }
}
