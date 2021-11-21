package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quickmaths.domain.DomainPlayer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class LeaderViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val db: FirebaseFirestore = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _navigateToPlayerDetail = MutableLiveData<String>()
    val navigateToPlayerDetail: LiveData<String>
        get() = _navigateToPlayerDetail

    private val _playersList = MutableLiveData<List<DomainPlayer>>()
    val playersList: LiveData<List<DomainPlayer>>
        get() = _playersList

    private val _startPosition = MutableLiveData<Int>()
    val startPosition: LiveData<Int>
        get() = _startPosition


    init {
        getPlayersListFromFirestore()
    }

    private fun getPlayersListFromFirestore() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val players = mutableListOf<DomainPlayer>()
                for (document in result) {
                    Timber.i("${document.id} => ${document.data}")
                    players.add(
                        DomainPlayer(
                            document.id,
                            document.data.getValue("name").toString(),
                            document.data.getValue("score").toString().toInt()
                        )
                    )
                    players.sortByDescending {
                        it.score
                    }
                    _playersList.value = players
                    scrollRecylerViewToCurrentPlayerPosition(players)
                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }
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
                _startPosition.value = playersList.indexOf(currentPlayer) + 3 // +3 to be centered
            } else {
                Timber.d("Current data: null")
            }
        }
    }

    fun onPlayerClicked(id: String){
        _navigateToPlayerDetail.value = id
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}

