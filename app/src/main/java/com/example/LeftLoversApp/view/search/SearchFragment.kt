package com.example.LeftLoversApp.view.search

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.FragmentHistoryBinding
import com.example.LeftLoversApp.databinding.FragmentHomeBinding
import com.example.LeftLoversApp.databinding.FragmentSearchBinding
import com.example.LeftLoversApp.model.SearchItem
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.adapter.ActiveFoodAdapter
import com.example.LeftLoversApp.view.history.HistoryAdapter
import com.example.LeftLoversApp.view.history.HistoryModelFactory
import com.example.LeftLoversApp.view.history.HistoryViewModel
import com.example.LeftLoversApp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private lateinit var dataStore: DataStore<Preferences>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }

    private val searchViewModel: SearchViewModel by viewModels {
        SearchModelFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvSearch.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvSearch.addItemDecoration(itemDecoration)
        setHasOptionsMenu(true)

        return binding.root
//        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        checkSession()
//        setupRecyclerView()
    }
    private fun checkSession() {
        searchViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                println("tokennya null")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                println("result $token")
                val searchView = binding.searchView

                callViewModel(token, "")

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        callViewModel(token, query)
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        // Handle text changes in the search view
                        // You can perform real-time filtering or suggestions here
                        callViewModel(token, newText)
                        return true
                    }
                })
//                searchViewModel.getSearch("Bearer $token", searchView).observe(this) {
//                    binding.progressBar.visibility = View.INVISIBLE
//                    val temp = it.data
//                    val rvAdapter = SearchAdapter(temp as List<SearchItem>)
//                    binding.rvSearch.adapter = rvAdapter
//                }
//                searchViewModel.getId().observe(this) { customerId ->
//                    searchViewModel.getHistory("Bearer $token",customerId)
//                }
            }
        }
    }
    private fun callViewModel(token: String, query: String) {
        searchViewModel.getSearch("Bearer $token", query).observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.INVISIBLE
            val temp = it.data
            val rvAdapter = SearchAdapter(temp as List<SearchItem>)
            binding.rvSearch.adapter = rvAdapter
        }
    }
}