package com.example.LeftLoversApp.view.keranjang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.databinding.FragmentHomeBinding
import com.example.LeftLoversApp.databinding.FragmentKeranjangBinding
import com.example.LeftLoversApp.view.home.HomeViewModel

class KeranjangFragment : Fragment() {

    private var _binding: FragmentKeranjangBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(KeranjangViewModel::class.java)

        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textKeranjang
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}