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
//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }
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

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//        val root: View = binding.root
//
//
//        val layoutManager = LinearLayoutManager(this)
//        binding.rvStory.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvStory.addItemDecoration(itemDecoration)
//
//        setupView()
//        setupLogout()
//    }

//    private var _binding: FragmentHomeBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

