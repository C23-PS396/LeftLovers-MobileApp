package com.example.LeftLoversApp.view.login



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.DataRepository
import com.example.LeftLoversApp.view.Injection

class LoginModelFactory private constructor(private val dataRepository: DataRepository, private val userPreference: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataRepository, userPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: LoginModelFactory? = null
        fun getInstance(
            userPreference: UserPreference
        ): LoginModelFactory =
            instance ?: synchronized(this) {
                instance ?: LoginModelFactory(
                    Injection.provideRepository(),
                    userPreference
                )
            }
    }
}