package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quickmaths.enums.BestScoreState
import com.example.quickmaths.util.SingleLiveEvent

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

    val onNewBestScoreState = MutableLiveData(BestScoreState.DEFAULT)
    val score = MutableLiveData<Int>()
    val highScore = MutableLiveData<Int>()



    init {
        onNewBestScoreState.value = BestScoreState.DEFAULT
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