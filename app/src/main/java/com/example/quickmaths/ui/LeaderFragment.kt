package com.example.quickmaths.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.adapters.PlayerListener
import com.example.quickmaths.adapters.PlayerStatsAdapter
import com.example.quickmaths.databinding.LeaderFragmentBinding
import com.example.quickmaths.viewmodels.LeaderViewModel
import com.example.quickmaths.viewmodelsfactory.LeaderViewModelFactory

class LeaderFragment : Fragment() {

    private lateinit var viewModel: LeaderViewModel
    private lateinit var binding: LeaderFragmentBinding
    private lateinit var adapter: PlayerStatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LeaderFragmentBinding.inflate(inflater)
        val viewModelFactory = LeaderViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LeaderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupObservation()
        setupOnClickItem()
        binding.playersList.adapter = adapter

        return binding.root
    }

    private fun setupOnClickItem() {
        adapter = PlayerStatsAdapter(PlayerListener { playerId ->
            Toast.makeText(context, playerId, Toast.LENGTH_LONG).show()
            viewModel.onPlayerClicked(playerId)
        })    }

    private fun setupObservation(){
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        viewModel.playersList.observe(viewLifecycleOwner,
        Observer { playersList ->
            adapter.submitList(playersList)
        })
        viewModel.startPosition.observe(viewLifecycleOwner,
        Observer { startPosition ->
            binding.playersList.scrollToPosition(startPosition)
        })
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}