package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.viewmodels.FirstTimeViewModel
import com.example.quickmaths.R
import com.example.quickmaths.databinding.FirstTimeFragmentBinding

class FirstTimeFragment : Fragment() {

    private lateinit var viewModel: FirstTimeViewModel
    private lateinit var binding: FirstTimeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FirstTimeFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(FirstTimeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupObservation()

        return binding.root
    }

    private fun setupObservation(){
        viewModel.onNavigateToHomeFragment().observe(viewLifecycleOwner,
        Observer {
            val navController = findNavController()
            navController.navigate(FirstTimeFragmentDirections.actionFirstTimeFragmentToHomeFragment())
        })
    }

}