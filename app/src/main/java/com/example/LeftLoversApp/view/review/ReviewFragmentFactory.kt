package com.example.LeftLoversApp.view.review

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.home.HomeViewModel
import com.example.LeftLoversApp.view.welcome.FoodRepository


class ReviewFragmentFactory private constructor(private val foodRepository: FoodRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewFragmentViewModel::class.java)) {
            return ReviewFragmentViewModel(foodRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: ReviewFragmentFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference
        ): ReviewFragmentFactory = instance ?: synchronized(this) {
            instance ?: ReviewFragmentFactory(
                Injection.provideFoodRepository(context),
                userPreference
            )
        }.also { instance = it }
    }
}