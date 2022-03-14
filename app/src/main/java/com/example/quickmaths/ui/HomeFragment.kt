package com.example.quickmaths.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quickmaths.BaseApplication
import com.example.quickmaths.R
import com.example.quickmaths.databinding.HomeFragmentBinding
import com.example.quickmaths.viewmodels.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = HomeFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        firebaseAnalytics = Firebase.analytics

        setupGSO()
        setupObservation()
        updateCurrentUserTv()

        return binding.root
    }

    private fun updateCurrentUserTv() {
        val currentUser = mAuth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.w( "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Timber.d("Current data: ${snapshot.data}")
                binding.tvCurrentUser.setText(snapshot.data?.getValue("name").toString())
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    private fun setupGSO(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)
    }

    private fun setupObservation(){
        viewModel.onPlayPressed().observe(viewLifecycleOwner,
            Observer {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "10")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name")
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
                firebaseAnalytics.logEvent("MyFirstEvent", bundle)

                val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_playFragment)
            })

        viewModel.onLeaderPressed().observe(viewLifecycleOwner,
        Observer {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_leaderFragment)
        })

        viewModel.onSettingsPressed().observe(viewLifecycleOwner,
        Observer {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_settingsFragment)
        })
        viewModel.onQuitPressed().observe(viewLifecycleOwner,
        Observer {
            requireActivity().finishAffinity()
        })
    }



}