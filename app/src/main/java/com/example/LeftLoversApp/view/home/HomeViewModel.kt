package com.example.LeftLoversApp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.LeftLoversApp.model.FoodResponses
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.welcome.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val foodRepository: FoodRepository, private val userPreference: UserPreference) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.logout()
        }
    }
    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
    fun getFoodActive(token: String) = foodRepository.getAllFood(token)
}
