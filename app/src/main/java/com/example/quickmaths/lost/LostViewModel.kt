package com.example.quickmaths.lost

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.SingleLiveEvent

class LostViewModel : ViewModel() {

    private val onNavigateToPlayFragment = SingleLiveEvent<Void>()
    private val onNavigateToLeaderFragment = SingleLiveEvent<Void>()

    fun onNavigateToPlayFragment(): LiveData<Void>{
        return onNavigateToPlayFragment
    }

    fun onPlayAgainPressed(){
        onNavigateToPlayFragment.call()
    }

    fun onNavigateToLeaderFragment(): LiveData<Void>{
        return onNavigateToLeaderFragment
    }

    fun onViewLeaderboardPressed(){
        onNavigateToLeaderFragment.call()
    }
}