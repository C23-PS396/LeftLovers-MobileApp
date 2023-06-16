package com.example.LeftLoversApp.view.rating

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.review.ReviewFragmentViewModel
import com.example.LeftLoversApp.view.welcome.FoodRepository


class RatingFactory private constructor(private val foodRepository: FoodRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatingViewModel::class.java)) {
            return RatingViewModel(foodRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: RatingFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference
        ): RatingFactory = instance ?: synchronized(this) {
            instance ?: RatingFactory(
                Injection.provideFoodRepository(context),
                userPreference
            )
        }.also { instance = it }
    }
}