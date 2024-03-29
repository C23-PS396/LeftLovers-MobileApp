package com.example.LeftLoversApp.api


import com.example.LeftLoversApp.local.RecommendationResponse
import com.example.LeftLoversApp.local.ReviewResponse
import com.example.LeftLoversApp.model.*
import com.example.LeftLoversApp.localData.FoodsItem
import com.example.LeftLoversApp.localData.StatusResponses
import com.example.LeftLoversApp.localData.TransactionResponse
import com.example.LeftLoversApp.localData.TransactionResponses
import com.example.LeftLoversApp.model.AddStoryResponse
import com.example.LeftLoversApp.model.FoodResponses
import com.example.LeftLoversApp.model.LoginResponse
import com.example.LeftLoversApp.model.RegisterResponse
import com.example.LeftLoversApp.model.StoryResponse
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/api/v1/auth/customer/signup")
    fun postRegister(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/api/v1/auth/customer/signin")
    fun postLogin(
        @Field("credential") credential: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/api/v1/gamification")
    fun getGamification(
    ): Call<List<GamificationResponseItem>>

    @GET("/api/v1/transaction")
    fun getHistory(
        @Header("Authorization") token: String,
        @Query("merchantId") merchantId: String?,
        @Query("customerId") customerId: String
    ): Call<HistoryResponse>

    @GET("/api/v1/search")
    fun getSearch(
        @Header("Authorization") token: String,
        @Query("query") query: String?
    ): Call<SearchResponse>

//    @GET("/api/v1/merchant")
//    fun getMerchant(
//        @Header("Authorization") token: String,
//        @Query("merchantId") merchantId: String,
//        @Query("sellerId") sellerId: String?,
//        @Query("isActive") isActive: Boolean?,
//        @Query("category") category: String?
//    ): Call<MerchantResponse>

    @GET("/api/v1/food")
    fun getFood(
        @Header("Authorization") token: String,
        @Query("merchantId") merchantId: String?,
        @Query("category") category: String,
        @Query("isActive") isActive: Boolean
    ): Call<FoodResponses>

    @PATCH("/api/v1/transaction/update")
    fun updateStatus(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Call<StatusResponses>

    @FormUrlEncoded
    @PATCH("/api/v1/transaction/update")
    fun updateStatusTransaction(
        @Header("Authorization") token: String,
        @Field("status") status: Int,
        @Field("transactionId") transactionId: String?
    ): Call<StatusResponses>

    @FormUrlEncoded
    @POST("/api/v1/review")
    fun postReview(
        @Header("Authorization") token: String,
        @Field("transactionId") transactionId: String?,
        @Field("rating") rating: Int,
        @Field("review") review: String
    ): Call<ReviewResponse>

//    @FormUrlEncoded
//    @POST("/api/v1/transaction")
//    fun postTransaction(
//        @Header("Authorization") token: String,
//        @Query("merchantId") merchantId: String,
//        @Query("customerId") customerId: String,
//        @Query("foods")
//    )
//    @FormUrlEncoded
//    @POST("/api/v1/transaction")
//    fun postTransaction(
//        @Header("Authorization") token: String,
//        @Field("merchantId") merchantId: String?,
//        @Field("customerId") customerId: String,
//        @Field("foods") foods: List<FoodsItem>
//    ): Call<TransactionResponse>
    @Headers("Content-Type: application/json")
    @POST("/api/v1/transaction")
    fun postTransaction(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Call<TransactionResponses>

    @GET("/api/v1/merchant/recommendation")
    fun getRecommendation(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: String,
    ): Call<RecommendationResponse>

//    @POST("/api/v1/transaction")
//    suspend fun postTransaction(
//        @Header("Authorization") token: String,
//        @Body request: TransactionRequest
//    ): Response<TransactionResponse>
//
//    data class TransactionRequest(
//        @SerializedName("merchantId") val merchantId: String,
//        @SerializedName("customerId") val customerId: String,
//        @SerializedName("foods") val foods: List<FoodItem>
//    )
//
//    data class FoodItem(
//        @SerializedName("foodId") val foodId: String,
//        @SerializedName("quantity") val quantity: Int
//    )


    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStories(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<AddStoryResponse>
}