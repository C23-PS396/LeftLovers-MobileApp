package com.example.LeftLoversApp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.LeftLoversApp.MainActivity
import com.example.LeftLoversApp.databinding.ActivityLoginBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.register.RegisterActivity
import com.example.LeftLoversApp.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginModelFactory.getInstance(UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    override fun onResume() {
        super.onResume()
        checkInfo()
    }
    private fun checkInfo() {
        loginViewModel.checkIsLogin().observe(this) {
            if (it) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }
        }
    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            if (binding.emailEditText.text.isNullOrEmpty() && binding.passwordEditText.text.isNullOrEmpty()) {

                if (binding.emailEditText.text.isNullOrEmpty()) {
                    binding.emailEditText.error = "email tidak boleh kosong"
                }
                if (binding.passwordEditText.text.isNullOrEmpty()) {
                    binding.passwordEditText.error = "password tidak memenuhi kriteria"
                }

            } else {
                val credential = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                val result = loginViewModel.login(credential, password)
                result.observe(this) {
                    when (it) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            val error = it.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            val temp = it.data
                            loginViewModel.saveToken(temp.token)

//                            loginViewModel.saveUserId(temp.data.id)
                            println(temp.data.id)

                            loginViewModel.saveUsername(temp.data.username)
                            loginViewModel.saveId(temp.data.id)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.titleTextView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, title, desc)
        }
        AnimatorSet().apply {
            playSequentially(together)
            start()
        }
    }

}