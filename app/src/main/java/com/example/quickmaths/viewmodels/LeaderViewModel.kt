package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.database.PlayerDatabaseDao

class LeaderViewModel(
    dataSource: PlayerDatabaseDao,
    application: Application
) : ViewModel() {

    private val _navigateToPlayerDetail = MutableLiveData<Long>()
    val navigateToPlayerDetail: LiveData<Long>
            get() = _navigateToPlayerDetail

    val database = dataSource

    val players = database.getAllPlayers()

    fun onPlayerClicked(id: Long){
        _navigateToPlayerDetail.value = id
    }

    fun onPlayerDetailNavigated() {
        _navigateToPlayerDetail.value = null
    }
}