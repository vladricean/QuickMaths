package com.example.quickmaths.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.databinding.LeaderFragmentBinding
import com.example.quickmaths.adapters.PlayerListener
import com.example.quickmaths.adapters.PlayerStatsAdapter
import com.example.quickmaths.viewmodels.LeaderViewModel
import com.example.quickmaths.viewmodelsfactory.LeaderViewModelFactory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber


class LeaderFragment : Fragment() {

    private lateinit var viewModel: LeaderViewModel
    private lateinit var binding: LeaderFragmentBinding
    private lateinit var adapter: PlayerStatsAdapter
    val db = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playerslist.observe(viewLifecycleOwner,
            Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LeaderFragmentBinding.inflate(inflater)
        val viewModelFactory = LeaderViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LeaderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = PlayerStatsAdapter(PlayerListener { playerId ->
            Toast.makeText(context, "${playerId}", Toast.LENGTH_LONG).show()
            viewModel.onPlayerClicked(playerId)
        })

        binding.playersList.adapter = adapter

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        ////////////////////////////////////////////////////////
                             // FIREBASE
        ////////////////////////////////////////////////////////

        val user = hashMapOf(
            "name" to "alex",
            "score" to 30
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Timber.w( "Error adding document", e)
            }

        // Read data from firebase
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Timber.d("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }

        return binding.root
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }



}