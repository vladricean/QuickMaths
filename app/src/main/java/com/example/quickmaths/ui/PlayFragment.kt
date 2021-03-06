package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.databinding.PlayFragmentBinding
import com.example.quickmaths.sharedEncryptedPrefs
import com.example.quickmaths.viewmodels.PlayViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
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

        setupObservation()

        return binding.root
    }

    private fun setupObservation(){
        viewModel.getScoreAndNavigateToLost.observe(viewLifecycleOwner,
        Observer {  score ->
            val navController = findNavController()
            navController.navigate(
                PlayFragmentDirections.actionPlayFragmentToLostFragment(
                    score
                )
            )
        })

    }

}