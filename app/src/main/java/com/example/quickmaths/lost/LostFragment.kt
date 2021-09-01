package com.example.quickmaths.lost

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.quickmaths.R
import com.example.quickmaths.databinding.LostFragmentBinding

class LostFragment : Fragment() {

    companion object {
        fun newInstance() = LostFragment()
    }

    private lateinit var viewModel: LostViewModel
    private lateinit var binding: LostFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = LostFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(LostViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args = LostFragmentArgs.fromBundle(requireArguments())
        binding.tvScore.setText(args.numPoints.toString())

        return binding.root
    }



}