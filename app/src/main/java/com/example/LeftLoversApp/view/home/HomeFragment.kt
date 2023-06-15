package com.example.LeftLoversApp.view.home

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.MainViewModel
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.FragmentHomeBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.login.LoginActivity
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.adapter.ActiveFoodAdapter
import com.example.LeftLoversApp.view.search.SearchFragment

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
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkSession()
    }

    private fun checkSession() {
        homeViewModel.checkToken().observe(this) {
            if (it == "null") {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val navController = requireActivity().findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.navigation_search)
            }
        }
        return super.onOptionsItemSelected(item)
    }



//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_home, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
//
//        val componentName = ComponentName(requireContext(), HomeFragment::class.java)
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = resources.getString(R.string.search_hint)
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String): Boolean {
////                mainViewModel.findRestaurant(query)
//                searchView.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(query: String): Boolean {
//                return false
//            }
//        })
//    }

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

