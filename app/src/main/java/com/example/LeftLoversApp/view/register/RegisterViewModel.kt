package com.example.LeftLoversApp.view.register

import androidx.lifecycle.ViewModel
import com.example.LeftLoversApp.view.DataRepository


class RegisterViewModel(private val dataRepository: DataRepository) : ViewModel() {
    fun registerUser(name: String, email: String, password: String) =
            dataRepository.register(name, email, password)
}
