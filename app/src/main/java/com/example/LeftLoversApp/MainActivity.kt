package com.example.LeftLoversApp

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
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.databinding.ActivityMainBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.login.LoginActivity
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.StoryAdapter
import com.example.LeftLoversApp.view.upload.UploadActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainModelFactory.getInstance(this, UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)


        setupView()
        setupBottomNavBar()
        setupLogout()
    }

    override fun onResume() {
        super.onResume()
        checkSession()
    }

    private fun checkSession() {
        mainViewModel.checkToken().observe(this) {
            if(it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }else {
//                setupGetAction("Bearer $it")
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
//        supportActionBar?.hide()
    }

    private fun setupBottomNavBar() {
        //        ----------------------------------
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history , R.id.navigation_profile, R.id.navigation_leaderboard, R.id.navigation_confirmPayment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        ----------------------------------
    }

    private fun setupLogout() {
//        binding.logoutButton.setOnClickListener {
//            mainViewModel.logout()
//        }
//        binding.postStoryButton.setOnClickListener {
//            val intent= Intent(this, UploadActivity::class.java)
//            startActivity(intent)
//        }
    }
//    private fun setupGetAction(token: String) {
//        mainViewModel.getStories(token).observe(this) {
//            when (it) {
//                is Result.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//                is Result.Error -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    val error = it.error
//                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                }
//                is Result.Success -> {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    val temp = it.data
//                    binding.rvStory.apply {
//                        val rvAdapter = StoryAdapter(temp)
//                        binding.rvStory.adapter = rvAdapter
//                    }
//                }
//            }
//        }
//    }

}