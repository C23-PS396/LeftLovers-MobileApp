package com.example.LeftLoversApp.view.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference



class WelcomeModelFactory (private val userPreference: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(userPreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
    companion object {
        @Volatile
        private var instance: WelcomeModelFactory? = null
        fun getInstance(userPreference: UserPreference): WelcomeModelFactory =
            instance ?: synchronized(this) {
                instance ?: WelcomeModelFactory(userPreference)
            }.also { instance = it }

    }

}
