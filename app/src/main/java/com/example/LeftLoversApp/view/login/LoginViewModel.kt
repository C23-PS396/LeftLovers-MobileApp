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
<<<<<<< HEAD

//    fun saveUserId(id: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            userPreference.saveUserId(id)
//        }
//    }

=======
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
>>>>>>> 0fd771ca8df55ad34ffc14c006b91c44077c3cfb
    fun checkIsLogin(): LiveData<Boolean> {
        return userPreference.isLogin().asLiveData()
    }

}
