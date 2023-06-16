package com.example.LeftLoversApp.view.welcome

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.LeftLoversApp.api.ApiService
import com.example.LeftLoversApp.local.MainExecutor
import com.example.LeftLoversApp.local.RecommendationResponse
import com.example.LeftLoversApp.local.ReviewResponse
import com.example.LeftLoversApp.localData.DaoFood
import com.example.LeftLoversApp.localData.DataStatus
import com.example.LeftLoversApp.localData.Food
import com.example.LeftLoversApp.localData.FoodsItem
import com.example.LeftLoversApp.localData.StatusResponses
import com.example.LeftLoversApp.localData.TransactionResponse
import com.example.LeftLoversApp.localData.TransactionResponses
import com.example.LeftLoversApp.model.ActiveFood
import com.example.LeftLoversApp.model.FoodResponses
import com.example.LeftLoversApp.model.LoginResponse
import com.example.LeftLoversApp.view.DataRepository
//import com.example.LeftLoversApp.localData.DaoFood
//import com.example.LeftLoversApp.localData.Food
import com.example.LeftLoversApp.view.Result
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRepository private constructor(private val apiService: ApiService, private val daoFood: DaoFood, private val mainExecutor: MainExecutor){
    private val getFoodResult = MediatorLiveData<Result<List<Food>>>()
    private val getFoodMerchResult = MediatorLiveData<Result<List<Food>>>()
    private val resultKeranjang = MediatorLiveData<Result<TransactionResponses>>()
    private val resultEditStatus = MutableLiveData<Result<StatusResponses>>()
    private val resultUpdateStatus = MutableLiveData<Result<StatusResponses>>()
    private val resultReview = MutableLiveData<Result<ReviewResponse>>()

    fun getAllFood(token: String):LiveData<Result<List<Food>>> {
        val merchantId = ""
        val category = ""
        val isActive = true
        getFoodResult.value = Result.Loading
        val client = apiService.getFood(token, merchantId, category, isActive)
        client.enqueue(object : Callback<FoodResponses> {
            override fun onResponse(call: Call<FoodResponses>, response: Response<FoodResponses>) {
                val responseBody = response.body()?.data
                val responseBodyList = ArrayList<Food>()
                if (response.isSuccessful) {
                    mainExecutor.diskIO.execute {
                        responseBody?.forEach {
                            val temp = Food(
                                it.id,
                                it.createdAt,
                                it.updatedAt,
                                it.name,
                                it.pictureUrl,
                                it.price,
                                it.merchantId,
                                it.category,
                                it.activeFood ?: ActiveFood("", 0, "", "", "", "", 0, false, "")
                            )
                            responseBodyList.add(temp)
                        }
                        daoFood.deleteFood()
                        daoFood.postFood(responseBodyList)
                    }

                }else {
                    Log.e(TAG, "Failed: Get All Food response unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FoodResponses>, t: Throwable) {
                getFoodResult.value = Result.Error(t.message.toString())
                Log.e(TAG, "Failed: Get All Food response failure - ${t.message.toString()}")
            }

        })
        val secTemp = daoFood.getFood()
        getFoodResult.addSource(secTemp) {
            getFoodResult.value = Result.Success(it)
        }
        return getFoodResult
    }
    fun getAllFoodMerch(token: String, merchId: String?):LiveData<Result<List<Food>>> {
//        val asd = "f0b99950-448e-40c0-b0ff-a4e904995182"
        val category = ""
        val isActive = true
        getFoodMerchResult.value = Result.Loading
        val client = apiService.getFood(token, merchId, category, isActive)
        client.enqueue(object : Callback<FoodResponses> {
            override fun onResponse(call: Call<FoodResponses>, response: Response<FoodResponses>) {
                val responseBody = response.body()?.data
                val responseBodyList = ArrayList<Food>()
                if (response.isSuccessful) {
                    mainExecutor.diskIO.execute {
                        responseBody?.forEach {
                            val temp = Food(
                                it.id,
                                it.createdAt,
                                it.updatedAt,
                                it.name,
                                it.pictureUrl,
                                it.price,
                                it.merchantId,
                                it.category,
                                it.activeFood ?: ActiveFood("", 0, "", "", "", "", 0, false, "")
                            )
                            responseBodyList.add(temp)
                        }
                        daoFood.deleteFood()
                        daoFood.postFood(responseBodyList)
                    }

                } else {
                    Log.e(TAG, "Failed: Get All Food Merch response unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FoodResponses>, t: Throwable) {
                getFoodMerchResult.value = Result.Error(t.message.toString())
                Log.e(TAG, "Failed: Get All Food Merch response failure - ${t.message.toString()}")
            }

        })
        val secTemp = daoFood.getFood()
        getFoodMerchResult.addSource(secTemp) {
            getFoodMerchResult.value = Result.Success(it)
        }
        return getFoodMerchResult
    }

    fun buyFood(token: String, merchantId: String?, customerId: String, foods: List<FoodsItem>): LiveData<Result<TransactionResponses>> {
        resultKeranjang.value = Result.Loading

        val gson = Gson()
        val requestBody = gson.toJson(mapOf(
            "merchantId" to merchantId,
            "customerId" to customerId,
            "foods" to foods
        ))

        val mediaType = "application/json".toMediaType()
        val requestBodyJson = requestBody.toRequestBody(mediaType)

        val client = apiService.postTransaction(token, requestBodyJson)
        client.enqueue(object : Callback<TransactionResponses> {
            override fun onResponse(
                call: Call<TransactionResponses>,
                response: Response<TransactionResponses>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    resultKeranjang.value = Result.Success(responseBody)
                } else {
                    resultKeranjang.value = Result.Error(OUT_OF_STOCK)
                    Log.e(FoodRepository.TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TransactionResponses>, t: Throwable) {
                resultKeranjang.value = Result.Error(LOGIN_ERROR_MESSAGE)
                Log.e(FoodRepository.TAG, "Failed: OnFailure Response - ${t.message.toString()}")
            }
        })

        return resultKeranjang
    }

    fun updateStatus(token: String, status: Int, transactionId: String): LiveData<Result<StatusResponses>> {
        resultUpdateStatus.value = Result.Loading
//        val gson = Gson()
//        val dataStatus = DataStatus(status, transactionId)
//        val requestBody = gson.toJson(dataStatus)
//            .toRequestBody("application/json".toMediaType())

        val client = apiService.updateStatusTransaction(token, status, transactionId)

        client.enqueue(object : Callback<StatusResponses> {
            override fun onResponse(call: Call<StatusResponses>, response: Response<StatusResponses>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    resultUpdateStatus.value = Result.Success(responseBody)
                } else {
                    resultUpdateStatus.value = Result.Error(EDIT_STATUS_ERROR_MESSAGE)
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StatusResponses>, t: Throwable) {
                resultEditStatus.value = Result.Error(EDIT_STATUS_ERROR_MESSAGE)
                Log.e(TAG, "Failed: OnFailure Response - ${t.message.toString()}")
            }
        })

        return resultUpdateStatus
    }
    fun postReview(token: String, transactionId: String?, rating: Int, review: String): LiveData<Result<ReviewResponse>> {

        val call = apiService.postReview(token, transactionId, rating, review)

        call.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        resultReview.value = Result.Success(responseBody)
                    } else {
                        resultReview.value = Result.Error("Response body is null")
                        val errorCode = response.code()
                        val errorMessage = response.message()
                        Log.e(TAG, "API call failed with error code $errorCode: $errorMessage")
                    }
                } else {
                    resultReview.value = Result.Error("Response unsuccessful")
                    val errorCode = response.code()
                    val errorMessage = response.message()
                    Log.e(TAG, "API call failed with error code $errorCode: $errorMessage")
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                resultReview.value = Result.Error(t.message ?: "Unknown error")
            }
        })

        return resultReview
    }



    companion object {
        val TAG = FoodRepository::class.java.simpleName
        private const val LOGIN_ERROR_MESSAGE = "Login failed, please try again."
        private const val REGISTER_ERROR_MESSAGE = "Register failed, please try again."
        private const val EDIT_STATUS_ERROR_MESSAGE = "Gagal mengupdate status"
        private const val OUT_OF_STOCK = "Maaf pesanan anda melebihi jumlah stok makanan yang ada"
        private const val REVIEW_STATUS_ERROR_MESSAGE = "Gagal memberikan review"
        @Volatile
        private var instance: FoodRepository? = null
        fun getInstance(
            apiService: ApiService, daoFood: DaoFood, mainExecutor: MainExecutor
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(apiService, daoFood, mainExecutor)
            }.also { instance = it }
    }
}