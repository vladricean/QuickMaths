package com.example.quickmaths.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.adapters.PlayerListener
import com.example.quickmaths.adapters.PlayerStatsAdapter
import com.example.quickmaths.databinding.LeaderFragmentBinding
import com.example.quickmaths.domain.DomainPlayer
import com.example.quickmaths.viewmodels.LeaderViewModel
import com.example.quickmaths.viewmodelsfactory.LeaderViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class LeaderFragment : Fragment() {

    private lateinit var viewModel: LeaderViewModel
    private lateinit var binding: LeaderFragmentBinding
    private lateinit var adapter: PlayerStatsAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LeaderFragmentBinding.inflate(inflater)
        val viewModelFactory = LeaderViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LeaderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        db = Firebase.firestore

        setupObservation()
        setupOnClickItem()
        getPlayersListFromFirestore()
        binding.playersList.adapter = adapter

        return binding.root
    }

    private fun getPlayersListFromFirestore() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val playersList = mutableListOf<DomainPlayer>()
                for (document in result) {
                    Timber.i("${document.id} => ${document.data}")
                    playersList.add(
                        DomainPlayer(
                            document.id,
                            document.data.getValue("name").toString(),
                            document.data.getValue("score").toString().toInt()
                        )
                    )
                }
                adapter.submitList(playersList)
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }
    }

    private fun setupOnClickItem() {
        adapter = PlayerStatsAdapter(PlayerListener { playerId ->
            Toast.makeText(context, playerId, Toast.LENGTH_LONG).show()
            viewModel.onPlayerClicked(playerId)
        })    }

    private fun setupObservation(){
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }



}