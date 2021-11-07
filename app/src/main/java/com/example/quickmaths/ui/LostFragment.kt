package com.example.quickmaths.ui

import android.content.Context
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
import com.example.quickmaths.database.PlayersDatabase
import com.example.quickmaths.databinding.LostFragmentBinding
import com.example.quickmaths.enums.BestScoreState
import com.example.quickmaths.viewmodels.LostViewModel
import com.example.quickmaths.viewmodelsfactory.LostViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LostFragment: Fragment() {

    private lateinit var binding: LostFragmentBinding
    private lateinit var viewModel: LostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.lost_fragment, container, false)
        viewModel = ViewModelProvider(this).get(LostViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        setupObservation()
        setScore()
//        getHighScoreFromSharedPref()

        return binding.root
    }



    private fun getHighScoreFromSharedPref() {
        val args = LostFragmentArgs.fromBundle(requireArguments())
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        if (sharedPref != null) {
            val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)

            if (sharedPref.getInt(
                    getString(R.string.saved_high_score_key),
                    defaultValue
                ) < args.numPoints
            ) {
                setNewBestScoreState()

                sharedPref.edit()
                    .putInt(getString(R.string.saved_high_score_key), args.numPoints)
                    .apply()
            }

            val highScore =
                sharedPref?.getInt(getString(R.string.saved_high_score_key), defaultValue)

            setHighScore(highScore)
        }
    }

    private fun setNewBestScoreState(){
        viewModel.onNewBestScoreState.value = BestScoreState.NEW_BEST_SCORE
    }

    private fun setHighScore(highScore: Int) {
        viewModel.highScore.value = highScore
    }

    private fun setScore() {
        val args = LostFragmentArgs.fromBundle(requireArguments())
        viewModel.score.value = args.numPoints
    }

    private fun setupObservation() {
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
        viewModel.numberOfPlayers.observe(viewLifecycleOwner,
            Observer { numberOfPlayers ->
                binding.playerStats.setText(numberOfPlayers.toString())
            })
        viewModel.getHighscoreFromFirestore()
    }


}