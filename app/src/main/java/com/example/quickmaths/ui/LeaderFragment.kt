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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class LeaderFragment : Fragment() {

    private lateinit var viewModel: LeaderViewModel
    private lateinit var binding: LeaderFragmentBinding
    private lateinit var adapter: PlayerStatsAdapter
    private lateinit var mAuth: FirebaseAuth
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

        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        setupObservation()
        setupOnClickItem()
        binding.playersList.adapter = adapter

        return binding.root
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
        viewModel.playersList.observe(viewLifecycleOwner,
        Observer { playersList ->
            adapter.submitList(playersList)
            scrollRecylerViewToCurrentPlayerPosition(playersList)
        })
    }

    private fun scrollRecylerViewToCurrentPlayerPosition(playersList: List<DomainPlayer>) {
        val currentUser = mAuth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.w( "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Timber.d("Current data: ${snapshot.data}")
                val currentPlayer = DomainPlayer(
                    currentUser.uid,
                    snapshot.data?.get("name").toString(),
                    snapshot.data?.get("score").toString().toInt()
                )
                val position = playersList.indexOf(currentPlayer) + 3 // +3 to be centered
                binding.playersList.scrollToPosition(position)
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}