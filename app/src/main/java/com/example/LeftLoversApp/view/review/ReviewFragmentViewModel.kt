package com.example.LeftLoversApp.view.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.welcome.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReviewFragmentViewModel (private val foodRepository: FoodRepository, private val userPreference: UserPreference) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
    fun postReview(token: String, transactionId: String, rating: Int, review: String) = foodRepository.postReview(token, transactionId, rating, review)

}