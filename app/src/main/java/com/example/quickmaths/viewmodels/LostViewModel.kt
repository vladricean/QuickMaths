package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quickmaths.enums.BestScoreState
import com.example.quickmaths.sharedEncryptedPrefs
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

    private val db = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    val onNewBestScoreState = MutableLiveData(BestScoreState.DEFAULT)
    val score = MutableLiveData<Int>()
    val highScore = MutableLiveData<Int>()

    init {
        onNewBestScoreState.value = BestScoreState.DEFAULT
        checkFirestoreScore()
    }

    private fun checkFirestoreScore() {
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
                if(firestoreScore < score.value!!) {
                    onNewBestScoreState.value = BestScoreState.NEW_BEST_SCORE
                    updateFirestoreScore(score.value!!)
                    highScore.value = score.value
                } else{
                    highScore.value = firestoreScore
                }
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    private fun updateFirestoreScore(score: Int) {
        db.collection("users").document(mAuth.currentUser!!.uid)
            .update("score", score)
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