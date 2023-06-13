package com.example.LeftLoversApp.view.profile

import androidx.lifecycle.*
import com.example.LeftLoversApp.model.LoginResponse
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val dataRepository: DataRepository, private val userPreference: UserPreference) : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun getProfile(): LiveData<LoginResponse?> {
        return dataRepository.loginResponse
    }

//    fun getUserId(): LiveData<String> {
//        return userPreference.getUserId().asLiveData()
//    }


}