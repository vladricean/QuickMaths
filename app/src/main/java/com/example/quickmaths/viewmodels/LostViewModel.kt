package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.quickmaths.database.Player
import com.example.quickmaths.database.PlayerDatabaseDao
import com.example.quickmaths.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LostViewModel(
    val database: PlayerDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val onNavigateToPlayFragment =
        SingleLiveEvent<Void>()
    private val onNavigateToLeaderFragment =
        SingleLiveEvent<Void>()

//    private var player = MutableLiveData<Player?>()

//    init {
//        initializePlayer()
//    }

//    private fun initializePlayer() {
//        viewModelScope.launch {
//            player.value = getPlayerFromDatabase()
//        }
//    }
//
//    private suspend fun getPlayerFromDatabase(): Player? {
//        var somePlayer = database.getPlayer()
//        somePlayer = null
//        return somePlayer
//    }

    fun onClickAddPlayer(){
        viewModelScope.launch {
            val newPlayer = Player()
            insert(newPlayer)
        }
    }

    private suspend fun insert(player: Player) {
        withContext(Dispatchers.IO) {
            database.insert(player)
        }    }

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