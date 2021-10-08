package com.example.quickmaths.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.quickmaths.R
import com.example.quickmaths.databinding.LeaderFragmentBinding


class LeaderFragment : Fragment() {

    private lateinit var binding: LeaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.leader_fragment, container, false
        )

        return binding.root
    }


}