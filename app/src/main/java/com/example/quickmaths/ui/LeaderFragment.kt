package com.example.quickmaths.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.R
import com.example.quickmaths.databinding.LeaderFragmentBinding
import com.example.quickmaths.recycler_adapters.PlayerStatsAdapter
import com.example.quickmaths.viewmodels.LeaderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderFragment : Fragment() {

    private lateinit var viewModel: LeaderViewModel
    private lateinit var binding: LeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LeaderFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(LeaderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = PlayerStatsAdapter()
        binding.playersList.adapter = adapter

        return binding.root
    }

}