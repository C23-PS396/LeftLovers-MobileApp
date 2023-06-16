package com.example.LeftLoversApp.view.recommendation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.ActivityDetailBinding
import com.example.LeftLoversApp.databinding.ActivityRecommendationBinding
import com.example.LeftLoversApp.local.RecommendationItem
import com.example.LeftLoversApp.localData.FoodsItem
import com.example.LeftLoversApp.model.HistoryItem
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.ActiveFoodAdapter
import com.example.LeftLoversApp.view.adapter.ActiveFoodDetailAdapter
import com.example.LeftLoversApp.view.adapter.CartManager
import com.example.LeftLoversApp.view.adapter.RecommendationMerchAdapter
import com.example.LeftLoversApp.view.confirmPayment.confirmPayment
import com.example.LeftLoversApp.view.detail.DetailModelFactory
import com.example.LeftLoversApp.view.detail.DetailViewModel
import com.example.LeftLoversApp.view.history.HistoryAdapter
import com.example.LeftLoversApp.view.login.LoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class RecommendationActivity : AppCompatActivity() {

    private lateinit var recommendationMerchAdapter: RecommendationMerchAdapter

    private lateinit var binding: ActivityRecommendationBinding
    private val recommendationViewModel: RecommendationViewModel by viewModels {
        RecommendationFactory.getInstance(this, UserPreference.getInstance(dataStore))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkSession()
        setupRecyclerView()

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.appBarColor))
        actionBar?.setLogo(R.drawable.logo)
        actionBar?.setDisplayUseLogoEnabled(true)
    }

    private fun checkSession() {
        recommendationViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                recommendationViewModel.getId().observe(this) { customerId ->
                    if (customerId == "null") {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        recommendationViewModel.getRecommendation("Bearer $token", customerId)

                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recommendationMerchAdapter = RecommendationMerchAdapter(emptyList())
        binding.rvStory.adapter = recommendationMerchAdapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        recommendationViewModel.recomList.observe(this) {recomList ->
            recommendationMerchAdapter.setRecommendationItems(recomList as List<RecommendationItem>)
        }
    }
}



