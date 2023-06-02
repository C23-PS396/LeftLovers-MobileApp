package com.example.LeftLoversApp.view

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.LeftLoversApp.api.ApiService
import com.example.LeftLoversApp.local.DaoStory
import com.example.LeftLoversApp.local.Story
import com.example.LeftLoversApp.model.StoryResponse
import com.example.LeftLoversApp.local.MainExecutor
import com.example.LeftLoversApp.model.AddStoryResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoryRepository private constructor(private val apiService: ApiService, private val daoStory: DaoStory, private val mainExecutor: MainExecutor){
    private val getStoryResult = MediatorLiveData<Result<List<Story>>>()
    private val postStoryResult = MediatorLiveData<Result<AddStoryResponse>>()

    fun getAllStory(token: String): LiveData<Result<List<Story>>> {
        getStoryResult.value = Result.Loading
        val client = apiService.getStories(token)
        client.enqueue(object : Callback<StoryResponse>{
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                val responseBody = response.body()?.listStory
                val responseBodyList = ArrayList<Story>()
                if (response.isSuccessful) {
                    mainExecutor.diskIO.execute {
                        responseBody?.forEach {
                            val temp = Story(
                                it.id,
                                it.name,
                                it.description,
                                it.photoUrl,
                                it.createdAt,
                                it.lat,
                                it.lon
                            )
                            responseBodyList.add(temp)
                        }
                        daoStory.deleteStory()
                        daoStory.postStory(responseBodyList)
                    }

                }else {
                    Log.e(ContentValues.TAG, "onFailure: gagal")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: fail")
            }

        })
        val secTemp = daoStory.getStory()
        getStoryResult.addSource(secTemp) {
            getStoryResult.value = Result.Success(it)
        }
        return getStoryResult
    }
    fun postStory(token: String, file: File, desc: String): LiveData<Result<AddStoryResponse>> {
        postStoryResult.postValue(Result.Loading)
        val description = desc.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )
        val client = apiService.postStories(token, imageMultipart, description)
        client.enqueue(object : Callback<AddStoryResponse>{
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    postStoryResult.postValue(Result.Success(responseBody))
                }else {
                    postStoryResult.postValue(Result.Error("Gagal mengirim data"))
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: fail")
            }
        })
        return postStoryResult
    }
    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService, daoStory: DaoStory, mainExecutor: MainExecutor
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, daoStory, mainExecutor)
            }.also { instance = it }
    }
}