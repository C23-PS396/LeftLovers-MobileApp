package com.example.LeftLoversApp.view.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.LeftLoversApp.api.ApiConfig
import com.example.LeftLoversApp.model.*
import com.example.LeftLoversApp.view.history.HistoryViewModel
import com.example.LeftLoversApp.view.welcome.FoodRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(foodRepository: FoodRepository, private val userPreference: UserPreference) : ViewModel() {


    private val _searchR = MutableLiveData<SearchResponse>()
    val searchR: LiveData<SearchResponse> = _searchR

    private val _searchList = MutableLiveData<List<SearchItem?>?>()
    val searchList: LiveData<List<SearchItem?>?> = _searchList

    fun checkToken(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }

    fun getId(): LiveData<String> {
        return userPreference.getId().asLiveData()
    }

    fun getSearch(token: String, inputString: String): LiveData<SearchResponse> {

        val client = ApiConfig.getApiService().getSearch(token, inputString)
        client.enqueue(object : Callback<SearchResponse> {
//            override fun onResponse(
//                call: Call<HistoryResponse>,
//                response: Response<HistoryResponse>
//            ) {
//                if (response.isSuccessful) {
//                    _historyR.value = response.body()
//                    val responseBody = _historyR.value?.data
//                    _historyList.value = responseBody
//                    Log.e(HistoryViewModel.TAG, "RESULT2: ${_historyList.value}")
//                } else {
//                    val errorCode = response.code()
//                    val errorMessage = response.message()
//                    Log.e(HistoryViewModel.TAG, "API call failed with error code $errorCode: $errorMessage")
//                    Log.e(HistoryViewModel.TAG, "RESULT: ${response.body()}")
//                    Log.e(HistoryViewModel.TAG, "RESULT2: ${response}")
//                }
//            }
//
//            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
//                Log.e(HistoryViewModel.TAG, "API call failed: ${t.message}")
//            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _searchR.value = response.body()
                    val responseBody = _searchR.value?.data
                    _searchList.value = responseBody
//                    Log.e(HistoryViewModel.TAG, "RESULT2: ${_searchList.value}")
//                    Log.e(HistoryViewModel.TAG, "RESULT2222: ${_searchR.value}")
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.message()
                    Log.e(HistoryViewModel.TAG, "API call failed with error code $errorCode: $errorMessage")
                    Log.e(HistoryViewModel.TAG, "RESULT: ${response.body()}")
                    Log.e(HistoryViewModel.TAG, "RESULT2: ${response}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(HistoryViewModel.TAG, "API call failed: ${t.message}")
            }
        })

        return _searchR
    }

}