package com.example.LeftLoversApp.view.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.LeftLoversApp.MainActivity
import com.example.LeftLoversApp.MainModelFactory
import com.example.LeftLoversApp.MainViewModel
import com.example.LeftLoversApp.R


import com.example.LeftLoversApp.databinding.ActivityDetailBinding
import com.example.LeftLoversApp.databinding.ActivityMainBinding
import com.example.LeftLoversApp.localData.FoodCartItem
import com.example.LeftLoversApp.localData.FoodsItem
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.ActiveFoodAdapter
import com.example.LeftLoversApp.view.adapter.ActiveFoodDetailAdapter
import com.example.LeftLoversApp.view.adapter.CartManager
import com.example.LeftLoversApp.view.confirmPayment.confirmPayment
import com.example.LeftLoversApp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        DetailModelFactory.getInstance(this, UserPreference.getInstance(dataStore))
    }
    private lateinit var cartManager: CartManager

    companion object {
        const val NAME = "name"
        const val MERCHANTID = "merchantId"
        const val IMG = "img"
    }
    override fun onBackPressed() {
        super.onBackPressed()
        cartManager.resetCart()
    }
    override fun onPause() {
        super.onPause()
        cartManager.resetCart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)
        val img = intent.getStringExtra(IMG)

        cartManager = CartManager.getInstance(this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)



        checkSession()
    }

    private fun checkSession() {
        detailViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                detailViewModel.getId().observe(this) { customerId ->
                    if (customerId == "null") {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        binding.purchaseButton.setOnClickListener( {
                            val merchId = intent.getStringExtra(MERCHANTID)
                            val cartItems = cartManager.getItems()
                            val foodItems = convertToFoodItems(cartItems)
                            setupPurchase("Bearer $token", merchId, customerId, foodItems)
                        })
                        setupGetAction("Bearer $token")

                    }
                }
            }
        }
    }

    private fun convertToFoodItems(cartItems: Map<String, Int>): List<FoodsItem> {
        val foodItems = mutableListOf<FoodsItem>()
        for (entry in cartItems.entries) {
            val foodItem = FoodsItem(entry.value, entry.key)
            foodItems.add(foodItem)
        }
        return foodItems
    }

    private fun setupGetAction(token: String) {
        val merchId = intent.getStringExtra(MERCHANTID)
        detailViewModel.getFoodActive(token, merchId).observe(this) {
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
                    val rvAdapter = ActiveFoodDetailAdapter(temp, cartManager)
                    binding.rvStory.adapter = rvAdapter
                }
            }
        }
    }

    private fun setupPurchase(token: String, merchId: String?, customerId: String, food: List<FoodsItem>) {
        detailViewModel.makeTransaction(token, merchId, customerId, food).observe(this) {
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
                    cartManager.resetCart()
                    detailViewModel.saveTransactionId(temp.data.id)
                    Log.d("Debug", "Transaction ID asdasdsad: ${temp.data.id}")
//                    val intent = Intent(this, confirmPayment::class.java)
//                    startActivity(intent)
//                    finish()
                    val fragment = confirmPayment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit()

                }
            }
        }
    }
}



