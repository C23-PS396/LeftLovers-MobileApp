package com.example.LeftLoversApp.view.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.welcome.FoodRepository


class HomeModelFactory private constructor(private val foodRepository: FoodRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(foodRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: HomeModelFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference
        ): HomeModelFactory = instance ?: synchronized(this) {
            instance ?: HomeModelFactory(
                Injection.provideFoodRepository(context),
                userPreference
            )
        }.also { instance = it }
    }
}