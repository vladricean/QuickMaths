package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickmaths.viewmodels.FirstTimeViewModel
import com.example.quickmaths.R

class FirstTimeFragment : Fragment() {

    companion object {
        fun newInstance() = FirstTimeFragment()
    }

    private lateinit var viewModel: FirstTimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_time_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstTimeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}