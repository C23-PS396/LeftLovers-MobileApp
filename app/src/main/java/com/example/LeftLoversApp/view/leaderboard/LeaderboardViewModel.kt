package com.example.LeftLoversApp.view.leaderboard

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.LeftLoversApp.api.ApiConfig
import com.example.LeftLoversApp.api.ApiService
import com.example.LeftLoversApp.local.Gamifikasi
import com.example.LeftLoversApp.local.Story
import com.example.LeftLoversApp.model.GamificationResponse
import com.example.LeftLoversApp.model.GamificationResponseItem
import com.example.LeftLoversApp.model.StoryResponse
import com.example.LeftLoversApp.view.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaderboardViewModel: ViewModel() {

    private val _leaderboardList = MutableLiveData<GamificationResponse>()
    val leaderboardList: LiveData<GamificationResponse> = _leaderboardList

    fun getLeaderboard():LiveData<GamificationResponse> {
//        _isLoadingMain.value = true

        val client = ApiConfig.getApiService().getGamification()

        client.enqueue(object : Callback<List<GamificationResponseItem>> {

            override fun onResponse(
                call: Call<List<GamificationResponseItem>>,
                response: Response<List<GamificationResponseItem>>
            ) {
                val responseBody = response.body()
                val responseBodyList = ArrayList<GamificationResponseItem>()

                if (response.isSuccessful) {
                    responseBody?.let {
                        for (item in responseBody) {
                            responseBodyList.add(item)
                        }
                        _leaderboardList.value = GamificationResponse(responseBodyList)
                    }
                    Log.e(ContentValues.TAG, "isi leaderboard  : ${_leaderboardList.value}")
                } else {
                    Log.e(ContentValues.TAG, "isi leaderboard gagal : ${responseBody}")
                }            }

            override fun onFailure(call: Call<List<GamificationResponseItem>>, t: Throwable) {
                Log.e(ContentValues.TAG, "API call failed: ${t.message}")
            }

        })
        return _leaderboardList
    }

}