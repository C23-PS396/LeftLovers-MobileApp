package com.example.LeftLoversApp.view.leaderboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.FragmentLeaderboardBinding
import com.example.LeftLoversApp.databinding.FragmentProfileBinding
import com.example.LeftLoversApp.model.GamificationResponseItem

class LeaderboardFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderboardFragment()
    }

    private lateinit var binding: FragmentLeaderboardBinding
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private lateinit var leaderboardViewModel: LeaderboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        leaderboardViewModel = ViewModelProvider(this).get(LeaderboardViewModel::class.java)
        setupRecyclerView()
        observeLeaderboardData()
        fetchLeaderboardData()
    }

    private fun setupRecyclerView() {
        leaderboardAdapter = LeaderboardAdapter(emptyList())
        binding.recyclerLeaderboard.adapter = leaderboardAdapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerLeaderboard.layoutManager = layoutManager
    }

    private fun observeLeaderboardData() {
        leaderboardViewModel.leaderboardList.observe(viewLifecycleOwner, Observer { leaderboardResponse ->
            leaderboardResponse?.let { response ->
                leaderboardAdapter.updateData((response.gamificationResponse
                    ?: emptyList()) as List<GamificationResponseItem>)
            }
        })
    }

    private fun fetchLeaderboardData() {
        leaderboardViewModel.getLeaderboard()
    }
}