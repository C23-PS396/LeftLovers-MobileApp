package com.example.LeftLoversApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val storyRepository: StoryRepository, private val userPreference: UserPreference): ViewModel() {
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.logout()
        }
    }
    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
    fun getStories(token: String) = storyRepository.getAllStory(token)
}