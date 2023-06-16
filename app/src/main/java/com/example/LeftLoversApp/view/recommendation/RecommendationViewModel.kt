package com.example.LeftLoversApp.view.recommendation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.LeftLoversApp.api.ApiConfig
import com.example.LeftLoversApp.local.RecommendationItem
import com.example.LeftLoversApp.local.RecommendationResponse
import com.example.LeftLoversApp.model.HistoryItem
import com.example.LeftLoversApp.model.HistoryResponse
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.history.HistoryViewModel
import com.example.LeftLoversApp.view.welcome.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendationViewModel(private val foodRepository: FoodRepository, private val userPreference: UserPreference) : ViewModel() {


    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.logout()
        }
    }
    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
    fun getFoodActive(token: String) = foodRepository.getAllFood(token)

    fun getId(): LiveData<String> {
        return userPreference.getId().asLiveData()
    }
    private val _historyR = MutableLiveData<RecommendationResponse>()
    val historyR: LiveData<RecommendationResponse> = _historyR

    private val _recomList = MutableLiveData<List<RecommendationItem?>?>()
    val recomList: LiveData<List<RecommendationItem?>?> = _recomList

//    fun getRecommendation(token: String, customerId: String) = foodRepository.getRecommendation(token, customerId)

    fun getRecommendation(token: String, customerId: String):LiveData<List<RecommendationItem?>?> {
        val client = ApiConfig.getApiService().getRecommendation(token, customerId)
        client.enqueue(object : Callback<RecommendationResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful) {
                    _historyR.value = response.body()
                    val responseBody = _historyR.value?.data

                    _recomList.value = responseBody
                    Log.e(TAG, "RESULT2: ${_recomList.value}")
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.message()
                    Log.e(TAG, "API call failed with error code $errorCode: $errorMessage")
                    Log.e(TAG, "RESULT: ${response.body()}")
                    Log.e(TAG, "RESULT2: ${response}")
                }
            }

            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}")
            }
        })

        return _recomList
    }
    companion object {
        private const val TAG = "Recommendation ViewModel"
    }
}