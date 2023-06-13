package com.example.LeftLoversApp.view.profile



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.DataRepository
import com.example.LeftLoversApp.view.Injection

class ProfileModelFactory private constructor(private val dataRepository: DataRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(dataRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: ProfileModelFactory? = null
        fun getInstance(
            userPreference1: Context,
            userPreference: UserPreference
        ): ProfileModelFactory =
            instance ?: synchronized(this) {
                instance ?: ProfileModelFactory(
                    Injection.provideRepository(),
                    userPreference
                )
            }
    }
}