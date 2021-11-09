package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.R
import com.example.quickmaths.databinding.LostFragmentBinding
import com.example.quickmaths.viewmodels.LostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LostFragment: Fragment() {

    private lateinit var binding: LostFragmentBinding
    private lateinit var viewModel: LostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.lost_fragment, container, false)
        viewModel = ViewModelProvider(this).get(LostViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        setupObservation()
        setScore()

        return binding.root
    }

    private fun setScore() {
        val args = LostFragmentArgs.fromBundle(requireArguments())
        viewModel.score.value = args.numPoints
    }

    private fun setupObservation() {
        viewModel.onNavigateToPlayFragment().observe(viewLifecycleOwner,
            Observer {
                val navController = findNavController()
                navController.navigate(LostFragmentDirections.actionLostFragmentToPlayFragment())
            })
        viewModel.onNavigateToLeaderFragment().observe(viewLifecycleOwner,
            Observer {
                val navController = findNavController()
                navController.navigate(LostFragmentDirections.actionLostFragmentToLeaderFragment())
            })
        viewModel.numberOfPlayers.observe(viewLifecycleOwner,
            Observer { numberOfPlayers ->
                binding.playerStats.setText(numberOfPlayers.toString())
            })
    }


}