package com.example.quickmaths.play

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.quickmaths.R
import com.example.quickmaths.databinding.PlayFragmentBinding

class PlayFragment : Fragment() {

    companion object {
        fun newInstance() = PlayFragment()
    }

    private lateinit var viewModel: PlayViewModel
    private lateinit var binding: PlayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PlayFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(PlayViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        settingUpLiveDataObservation()

        return binding.root
    }

    private fun settingUpLiveDataObservation(){
        viewModel.userAnswer.observe(viewLifecycleOwner,
        Observer {
            viewModel.checkUserAnswer()
        })
    }

}