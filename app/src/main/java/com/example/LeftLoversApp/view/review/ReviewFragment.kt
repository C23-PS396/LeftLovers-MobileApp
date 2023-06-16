package com.example.LeftLoversApp.view.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.FragmentConfirmPaymentBinding
import com.example.LeftLoversApp.databinding.FragmentReviewBinding
import com.example.LeftLoversApp.localData.FoodsItem
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.confirmPayment.confirmPayment
import com.example.LeftLoversApp.view.confirmPayment.confirmPaymentModelFactory
import com.example.LeftLoversApp.view.confirmPayment.confirmPaymentViewModel
import com.example.LeftLoversApp.view.login.LoginActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class ReviewFragment : Fragment() {
    companion object {
        const val STATUS = "id"
        const val TRANSACTIONID = "status"
    }
    private var ratingValue: Int = 0
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var binding: FragmentReviewBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }
    private val reviewFragmentViewModel: ReviewFragmentViewModel by viewModels {
        ReviewFragmentFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        checkSession()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                // Menangani perubahan rating
                ratingValue = rating.toInt()
                Toast.makeText(
                    requireContext(),
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
        reviewFragmentViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                println("tokennya null")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                val textReview = binding.editTextInput.toString()
                println("ini isi dari transaction id awokawok: $TRANSACTIONID")
                setupRating(token, TRANSACTIONID,ratingValue, textReview)
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
    private fun setupRating(token: String, transactionId: String, rating: Int, review: String) {
        reviewFragmentViewModel.postReview(token, transactionId, rating, review).observe(this) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val error = it.error
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
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