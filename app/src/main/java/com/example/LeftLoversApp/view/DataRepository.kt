package com.example.LeftLoversApp.view

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.LeftLoversApp.api.ApiService
import com.example.LeftLoversApp.model.LoginResponse
import com.example.LeftLoversApp.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataRepository private constructor(
    private val apiService: ApiService,
) {
    private val resultRegister = MediatorLiveData<Result<RegisterResponse>>()
    private val resultLogin = MediatorLiveData<Result<LoginResponse>>()

    fun register(email: String, password: String, username: String): LiveData<Result<RegisterResponse>> {
        resultRegister.value = Result.Loading
        val client = apiService.postRegister(email, password, username)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    resultRegister.value = Result.Success(responseBody)
                } else {
                    resultRegister.value = Result.Error(REGISTER_ERROR_MESSAGE)
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                resultRegister.value = Result.Error(LOGIN_ERROR_MESSAGE)
                Log.e(TAG, "Failed: OnFailure Response - ${t.message.toString()}")
            }
        })
        return resultRegister
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> {
        resultLogin.value = Result.Loading
        val client = apiService.postLogin(
            email,
            password
        )

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        resultLogin.value = Result.Success(responseBody)
                    } else {
                        resultLogin.value = Result.Error(LOGIN_ERROR_MESSAGE)
                        Log.e(TAG, "Failed: Login Information is null")
                    }
                } else {
                    resultLogin.value = Result.Error(LOGIN_ERROR_MESSAGE)
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                resultLogin.value = Result.Error(LOGIN_ERROR_MESSAGE)
                Log.e(TAG, "Failed: OnFailure Response - ${t.message.toString()}")
            }
        })

        return resultLogin
    }

    companion object {
        private val TAG = DataRepository::class.java.simpleName
        private const val LOGIN_ERROR_MESSAGE = "Login failed, please try again."
        private const val REGISTER_ERROR_MESSAGE = "Register failed, please try again."
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(apiService)
            }.also { instance = it }
    }
}