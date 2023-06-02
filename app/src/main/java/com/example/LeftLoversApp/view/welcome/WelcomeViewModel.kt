package com.example.LeftLoversApp.view.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.LeftLoversApp.model.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(private val userPreference: UserPreference): ViewModel() {
    fun setIsLogin(isLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.setIsLogin(isLogin)
        }
    }
}
