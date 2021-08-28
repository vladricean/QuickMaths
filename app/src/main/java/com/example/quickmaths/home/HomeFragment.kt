package com.example.quickmaths.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.R
import com.example.quickmaths.databinding.HomeFragmentBinding
import kotlinx.coroutines.flow.collect

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = HomeFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        navigationHandler()

        return binding.root
    }

    private fun navigationHandler(){
        viewModel.onPlayPressed().observe(viewLifecycleOwner,
            Observer {
                val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_playFragment)
            })

        viewModel.onLeaderPressed().observe(viewLifecycleOwner,
        Observer {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_leaderFragment)
        })

        viewModel.onSettingsPressed().observe(viewLifecycleOwner,
        Observer {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_settingsFragment)
        })
    }



}