package com.example.LeftLoversApp.view.confirmPayment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.home.HomeViewModel
import com.example.LeftLoversApp.view.welcome.FoodRepository


class confirmPaymentModelFactory private constructor(private val foodRepository: FoodRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(confirmPaymentViewModel::class.java)) {
            return confirmPaymentViewModel(foodRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: confirmPaymentModelFactory? = null

        fun getInstance(context: Context, userPreference: UserPreference
        ): confirmPaymentModelFactory = instance ?: synchronized(this) {
            instance ?: confirmPaymentModelFactory(
                Injection.provideFoodRepository(context),
                userPreference
            )
        }.also { instance = it }
    }
}