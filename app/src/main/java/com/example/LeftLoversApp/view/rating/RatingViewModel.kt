package com.example.LeftLoversApp.view.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.welcome.FoodRepository


class RatingViewModel (private val foodRepository: FoodRepository, private val userPreference: UserPreference) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
    fun postReview(token: String, transactionId: String?, rating: Int, review: String) = foodRepository.postReview(token, transactionId, rating, review)

}