package com.example.LeftLoversApp.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.StoryRepository
import java.io.File

class UploadViewModel(private val storyRepository: StoryRepository, private val userPreference: UserPreference): ViewModel() {
    fun postStory(token: String, file: File, desc: String) =
        storyRepository.postStory(token, file, desc)

    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
}