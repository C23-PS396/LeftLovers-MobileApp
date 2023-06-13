package com.example.LeftLoversApp.view.history

import android.util.Log
import androidx.lifecycle.*
import com.example.LeftLoversApp.api.ApiConfig
import com.example.LeftLoversApp.model.*
import com.example.LeftLoversApp.view.welcome.FoodRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(foodRepository: FoodRepository, private val userPreference: UserPreference): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is History Fragment"
    }
    val text: LiveData<String> = _text

//    private val _historyList = MutableLiveData<HistoryResponse>()
//    val historyList: LiveData<HistoryResponse> = _historyList

    private val _historyR = MutableLiveData<HistoryResponse>()
    val historyR: LiveData<HistoryResponse> = _historyR

    private val _historyList = MutableLiveData<List<HistoryItem?>?>()
    val historyList: LiveData<List<HistoryItem?>?> = _historyList

    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }

    fun getId(): LiveData<String> {
        return userPreference.getId().asLiveData()
    }

    fun getHistory(token: String, customerId: String):LiveData<HistoryResponse> {
        val merchantId = null
        val client = ApiConfig.getApiService().getHistory(token, merchantId, customerId)
        client.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                if (response.isSuccessful) {
                    _historyR.value = response.body()
                    val responseBody = _historyR.value?.data

                    _historyList.value = responseBody
                    Log.e(TAG, "RESULT2: ${_historyList.value}")
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.message()
                    Log.e(TAG, "API call failed with error code $errorCode: $errorMessage")
                    Log.e(TAG, "RESULT: ${response.body()}")
                    Log.e(TAG, "RESULT2: ${response}")
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}")
            }
        })

        return _historyR
    }

    companion object {
        private const val TAG = "HistoryViewModel"
    }
}