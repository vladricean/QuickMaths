package com.example.quickmaths.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.R
import com.example.quickmaths.database.PlayerDatabase
import com.example.quickmaths.databinding.LeaderFragmentBinding
import com.example.quickmaths.recycler_adapters.PlayerStatsAdapter
import com.example.quickmaths.viewmodels.LeaderViewModel
import com.example.quickmaths.viewmodelsfactory.LeaderViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

class LeaderFragment : Fragment() {

    private lateinit var binding: LeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.leader_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao
        val viewModelFactory = LeaderViewModelFactory(dataSource, application)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(LeaderViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val adapter = PlayerStatsAdapter()
        binding.playersList.adapter = adapter

        viewModel.players.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
            }
        })

        return binding.root
    }



}