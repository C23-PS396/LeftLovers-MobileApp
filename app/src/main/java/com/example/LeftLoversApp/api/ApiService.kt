package com.example.LeftLoversApp.api

import com.example.LeftLoversApp.model.AddStoryResponse
import com.example.LeftLoversApp.model.LoginResponse
import com.example.LeftLoversApp.model.RegisterResponse
import com.example.LeftLoversApp.model.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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