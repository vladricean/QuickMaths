package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quickmaths.enums.BestScoreState
import com.example.quickmaths.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class LostViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val onNavigateToPlayFragment =
        SingleLiveEvent<Void>()
    private val onNavigateToLeaderFragment =
        SingleLiveEvent<Void>()

    private val _numberOfPlayers = MutableLiveData<Int>()
    val numberOfPlayers: LiveData<Int>
            get() = _numberOfPlayers
    private val db = Firebase.firestore
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val onNewBestScoreState = MutableLiveData(BestScoreState.DEFAULT)
    val score = MutableLiveData<Int>()
    val highScore = MutableLiveData<Int>()

    init {
        onNewBestScoreState.value = BestScoreState.DEFAULT
    }

    fun getHighscoreFromFirestore() {
        val currentUser = mAuth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.w( "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Timber.d("Current data: ${snapshot.data}")
                val firestoreScore = snapshot.data?.getValue("score").toString().toInt()
                highScore.value = firestoreScore
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    fun onNewBestScoreState(): MutableLiveData<BestScoreState> {
        return onNewBestScoreState
    }

    fun onNavigateToPlayFragment(): LiveData<Void> {
        return onNavigateToPlayFragment
    }

    fun onPlayAgainPressed() {
        onNavigateToPlayFragment.call()
    }

    fun onNavigateToLeaderFragment(): LiveData<Void> {
        return onNavigateToLeaderFragment
    }

    fun onViewLeaderboardPressed() {
        onNavigateToLeaderFragment.call()
    }


}