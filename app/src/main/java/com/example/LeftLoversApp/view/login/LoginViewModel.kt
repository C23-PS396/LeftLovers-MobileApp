package com.example.LeftLoversApp.view.login

import androidx.lifecycle.*
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val dataRepository: DataRepository, private val userPreference: UserPreference): ViewModel() {
    fun login(credential: String, password: String) = dataRepository.login(credential, password)
    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.saveToken(token)
        }
    }
    fun saveUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.saveUsername(username)
        }
    }
    fun saveId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreference.saveId(id)
        }
    }
    fun checkIsLogin(): LiveData<Boolean> {
        return userPreference.isLogin().asLiveData()
    }

}
