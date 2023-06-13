package com.example.LeftLoversApp.view.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.databinding.FragmentHistoryBinding
import com.example.LeftLoversApp.model.HistoryItem
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var dataStore: DataStore<Preferences>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }

    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryModelFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        checkSession()
        setupRecyclerView()
    }
    private fun checkSession() {
        historyViewModel.checkToken().observe(this) { token ->
            if (token == "null") {
                println("tokennya null")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                println("result $token")
                historyViewModel.getId().observe(this) { customerId ->
                    historyViewModel.getHistory("Bearer $token",customerId)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(emptyList())
        binding.recyclerHistory.adapter = historyAdapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHistory.layoutManager = layoutManager

        historyViewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            historyAdapter.setHistoryItems(historyList as List<HistoryItem>)
        }
    }

}