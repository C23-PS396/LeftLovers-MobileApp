package com.example.LeftLoversApp.view.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.LeftLoversApp.localData.FoodsItem
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Injection
import com.example.LeftLoversApp.view.home.HomeViewModel
import com.example.LeftLoversApp.view.welcome.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(private val foodRepository: FoodRepository, private val userPreference: UserPreference) : ViewModel() {

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
    fun getId(): LiveData<String> {
        return userPreference.getId().asLiveData()
    }

    fun makeTransaction(token: String, merchId: String?, customerId: String, listFood: List<FoodsItem>) = foodRepository.buyFood(token, merchId,customerId,  listFood)
    fun getFoodActive(token: String, merchId: String?) = foodRepository.getAllFoodMerch(token, merchId)

    fun saveTransactionId(transactionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.saveTransactionId(transactionId)
        }
    }
}