package com.example.quickmaths.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.R
import com.example.quickmaths.databinding.LeaderFragmentBinding
import com.example.quickmaths.viewmodels.LeaderViewModel
import com.example.quickmaths.viewmodelsfactory.LeaderViewModelFactory


class LeaderFragment : Fragment() {

    private lateinit var viewModel: LeaderViewModel
    private lateinit var binding: LeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LeaderFragmentBinding.inflate(inflater)
        val viewModelFactory = LeaderViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LeaderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        return binding.root
    }


}