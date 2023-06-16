package com.example.LeftLoversApp.view.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.databinding.FragmentHomeBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.login.LoginActivity
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.ActiveFoodAdapter
import com.example.LeftLoversApp.view.adapter.CartManager

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class HomeFragment : Fragment() {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var binding: FragmentHomeBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }
    private val homeViewModel: HomeViewModel by viewModels {
        HomeModelFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }
//    private lateinit var cartManager: CartManager
//    override fun onBackPressed() {
//        super.onBackPressed()
//        cartManager.resetCart()
//    }
//    override fun onPause() {
//        super.onPause()
//        cartManager.resetCart()
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        checkSession()
    }
    private fun checkSession() {
        homeViewModel.checkToken().observe(this) {
            if(it == "null") {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }else {
                setupGetAction("Bearer $it")
            }
        }
    }

    private fun setupGetAction(token: String) {
        homeViewModel.getFoodActive(token).observe(this) {
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
                    val temp = it.data
                    val rvAdapter = ActiveFoodAdapter(temp)
                    binding.rvStory.adapter = rvAdapter
                    }
                }
            }
        }
    }

