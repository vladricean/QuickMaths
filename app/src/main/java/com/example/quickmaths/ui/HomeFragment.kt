package com.example.quickmaths.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.MainActivity
import com.example.quickmaths.R
import com.example.quickmaths.databinding.HomeFragmentBinding
import com.example.quickmaths.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
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

        setupObservation()

        return binding.root
    }

    private fun setupObservation(){
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
        viewModel.onLogoutPressed().observe(viewLifecycleOwner,
        Observer {
            Firebase.auth.signOut()
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_signInActivity)
            activity?.finish()
        })
    }



}