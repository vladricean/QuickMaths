package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickmaths.database.Player
import com.example.quickmaths.database.PlayerDatabaseDao
import com.example.quickmaths.enums.BestScoreState
import com.example.quickmaths.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LostViewModel(
    val database: PlayerDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val onNavigateToPlayFragment =
        SingleLiveEvent<Void>()
    private val onNavigateToLeaderFragment =
        SingleLiveEvent<Void>()

    private var player = MutableLiveData<Player?>()

    private val _numberOfPlayers = MutableLiveData<Int>()
    val numberOfPlayers: LiveData<Int>
            get() = _numberOfPlayers

    val onNewBestScoreState = MutableLiveData(BestScoreState.DEFAULT)
    val score = MutableLiveData<Int>()
    val highScore = MutableLiveData<Int>()

    fun onClickAddPlayer(){
        viewModelScope.launch {
            val newPlayer = Player()
            insert(newPlayer)
            player.value = get(1)
            _numberOfPlayers.value = getNumberOfPlayers()
        }
    }

    init {
        onNewBestScoreState.value = BestScoreState.DEFAULT
    }

    fun onNewBestScoreState(): MutableLiveData<BestScoreState> {
        return onNewBestScoreState
    }

    private suspend fun getNumberOfPlayers(): Int? {
        return database.getNumberOfPlayers()
    }

    private suspend fun insert(player: Player) {
            database.insert(player)
        }

    private suspend fun get(playerID: Long) : Player {
        return database.get(playerID)!!
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