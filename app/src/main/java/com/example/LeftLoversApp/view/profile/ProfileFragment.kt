package com.example.LeftLoversApp.view.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.databinding.FragmentProfileBinding
import com.example.LeftLoversApp.model.UserPreference
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.LeftLoversApp.MainActivity
import com.example.LeftLoversApp.MainModelFactory
import com.example.LeftLoversApp.MainViewModel
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.view.leaderboard.LeaderboardFragment
import com.example.LeftLoversApp.view.login.LoginActivity
import com.example.LeftLoversApp.view.recommendation.RecommendationActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class ProfileFragment : Fragment() {

//    private var _binding: FragmentProfileBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainModelFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileModelFactory.getInstance(requireContext(), UserPreference.getInstance(dataStore))
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataStore = requireContext().dataStore
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val textView2: TextView = binding.tvItemTitle
//            textView2.text = profileViewModel.getProfile().value?.data?.email

        profileViewModel.getUsername().observe(viewLifecycleOwner) {
            textView2.text = "$it"
            println("$it")

        }
        setButton()
        return root


    }


    private fun setButton() {
        binding.leaderboardButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_leaderboard)
        }

        binding.logoutButton.setOnClickListener {
            mainViewModel.logout()
        }
        binding.recommendation.setOnClickListener {
            val intent = Intent(requireContext(), RecommendationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


}