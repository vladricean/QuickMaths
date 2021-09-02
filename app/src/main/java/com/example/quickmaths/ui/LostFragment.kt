package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.R
import com.example.quickmaths.database.PlayerDatabase
import com.example.quickmaths.databinding.LostFragmentBinding
import com.example.quickmaths.viewmodels.LostViewModel
import com.example.quickmaths.viewmodelsfactory.LostViewModelFactory

class LostFragment : Fragment() {

    private lateinit var binding: LostFragmentBinding
    private lateinit var viewModel: LostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.lost_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao
        val viewModelFactory = LostViewModelFactory(dataSource, application)
        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(LostViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        setScore()
        settingUpLiveDataObservation()

        return binding.root
    }

    private fun settingUpLiveDataObservation() {
        viewModel.onNavigateToPlayFragment().observe(viewLifecycleOwner,
            Observer {
                val navController = findNavController()
                navController.navigate(LostFragmentDirections.actionLostFragmentToPlayFragment())
            })
        viewModel.onNavigateToLeaderFragment().observe(viewLifecycleOwner,
            Observer {
                val navController = findNavController()
                navController.navigate(LostFragmentDirections.actionLostFragmentToLeaderFragment())
            })
    }

    private fun setScore() {
        val args = LostFragmentArgs.fromBundle(requireArguments())
        binding.tvScore.setText(args.numPoints.toString())
    }


}