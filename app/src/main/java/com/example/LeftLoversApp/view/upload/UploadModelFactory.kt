package com.example.LeftLoversApp.view.upload

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.StoryRepository

class UploadModelFactory private constructor(private val storyRepository: StoryRepository, private val userPreference: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(storyRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: UploadModelFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference
        ): UploadModelFactory = instance ?: synchronized(this) {
            instance ?: UploadModelFactory(
                Injection.provideStoryRepository(context),
                userPreference
            )
        }.also { instance = it }
    }
}