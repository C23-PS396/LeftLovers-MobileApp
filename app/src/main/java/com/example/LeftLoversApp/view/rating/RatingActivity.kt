package com.example.LeftLoversApp.view.rating

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.ActivityDetailBinding
import com.example.LeftLoversApp.databinding.ActivityRatingBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.CartManager
import com.example.LeftLoversApp.view.detail.DetailActivity
import com.example.LeftLoversApp.view.detail.DetailModelFactory
import com.example.LeftLoversApp.view.detail.DetailViewModel
import com.example.LeftLoversApp.view.login.LoginActivity
import com.example.LeftLoversApp.view.review.ReviewFragment

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private val ratingViewModel: RatingViewModel by viewModels {
        RatingFactory.getInstance(this, UserPreference.getInstance(dataStore))
    }
    private var ratingValue: Int = 0
    companion object {
        const val STATUS = "status"
        const val TRANSACTIONID = "id"
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_rating)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
//        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvStory.addItemDecoration(itemDecoration)
    binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
        // Menangani perubahan rating
        ratingValue = rating.toInt()
        Toast.makeText(
            this,
            "Rating: $ratingValue",
            Toast.LENGTH_SHORT
        ).show()
    }


    binding.btnSubmit.setOnClickListener {
        // Mengirim ulasan
//            val reviewText = binding.etReview.text.toString()

        // Lakukan pengiriman ulasan ke server atau lakukan tindakan yang sesuai
        // Gunakan nilai ratingValue di sini atau dalam method lain yang diinginkan

//            Toast.makeText(
//                requireContext(),
//                "Ulasan terkirim. Rating: $ratingValue, Ulasan: $reviewText",
//                Toast.LENGTH_SHORT
//            ).show()
        checkSession()
    }
    }
    private fun checkSession() {
        ratingViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                println("tokennya null")
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                val textReview = binding.editTextInput.text.toString()
                val transactionID = intent.getStringExtra(TRANSACTIONID)
                println("ini isi dari transaction id awokawok: ${transactionID}")
                setupRating("Bearer $token", transactionID,ratingValue, textReview)
//                confirmPaymentViewModel.getTransactionId().observe(this) { transactionId ->
//                    if (transactionId == "null") {
//                        println("transaksiid nya null")
//                        val intent = Intent(requireContext(), LoginActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
//                    } else {
//                        binding.updateStatus.setOnClickListener( {
////                            setupPurchase("Bearer $token", merchId, customerId, foodItems)
//                            println("ini transaksi id nya besti $transactionId")
//                            confirmPaymentViewModel.updateStatus("Bearer $token", 2, transactionId)
////                            confirmPaymentViewModel.resetTransactionId()
//                        })
//
//                    }
//                }
            }
        }
    }
    private fun setupRating(token: String, transactionId: String?, rating: Int, review: String) {
        ratingViewModel.postReview(token, transactionId, rating, review).observe(this) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val error = it.error
                    Toast.makeText(this, "Pesanan anda belum selesai, gagal memberi rating! " + error, Toast.LENGTH_SHORT).show();

                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
//                    val temp = it.data
//                    cartManager.resetCart()
//                    detailViewModel.saveTransactionId(temp.data.id)
//                    Log.d("Debug", "Transaction ID asdasdsad: ${temp.data.id}")
////                    val intent = Intent(this, confirmPayment::class.java)
////                    startActivity(intent)
////                    finish()
//                    val fragment = confirmPayment()
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .commit()

                }
            }
        }
    }
}