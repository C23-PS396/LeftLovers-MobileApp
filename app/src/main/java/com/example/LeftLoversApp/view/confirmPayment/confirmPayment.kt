package com.example.LeftLoversApp.view.confirmPayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels

import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.FragmentConfirmPaymentBinding
import com.example.LeftLoversApp.databinding.FragmentHomeBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.ActiveFoodAdapter
import com.example.LeftLoversApp.view.detail.DetailActivity
import com.example.LeftLoversApp.view.home.HomeModelFactory
import com.example.LeftLoversApp.view.home.HomeViewModel
import com.example.LeftLoversApp.view.login.LoginActivity



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class confirmPayment : Fragment() {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var binding: FragmentConfirmPaymentBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }
    private val confirmPaymentViewModel: confirmPaymentViewModel by viewModels {
        confirmPaymentModelFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmPaymentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkSession()
    }

    private fun checkSession() {
        confirmPaymentViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                println("tokennya null")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                confirmPaymentViewModel.getTransactionId().observe(this) { transactionId ->
                    if (transactionId == "null") {
                        println("transaksiid nya null")
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        binding.updateStatus.setOnClickListener {
//                            setupPurchase("Bearer $token", merchId, customerId, foodItems)
                            println("ini transaksi id nya besti $transactionId")
                            confirmPaymentViewModel.updateStatus("Bearer $token", 2, transactionId)
//                            val navController = findNavController()
//                            navController.navigate(R.id.navigation_history)
//                            confirmPaymentViewModel.resetTransactionId()

                        }

                        binding.updateStatus.setOnClickListener {
//                            findNavController().navigate(R.id.navigation_home)
                        }



                    }
                }
            }
        }
    }
}