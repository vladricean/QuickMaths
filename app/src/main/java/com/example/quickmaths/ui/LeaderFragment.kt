package com.example.quickmaths.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickmaths.R
import com.example.quickmaths.viewmodels.LeaderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderFragment()
    }

    private lateinit var viewModel: LeaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.leader_fragment, container, false)
    }

}