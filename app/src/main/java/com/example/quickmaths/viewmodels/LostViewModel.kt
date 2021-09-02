package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.database.PlayerDatabaseDao
import com.example.quickmaths.util.SingleLiveEvent

class LostViewModel(
    val database: PlayerDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val onNavigateToPlayFragment =
        SingleLiveEvent<Void>()
    private val onNavigateToLeaderFragment =
        SingleLiveEvent<Void>()

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