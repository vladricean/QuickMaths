package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.quickmaths.database.PlayerDatabaseDao

class LeaderViewModel(
    dataSource: PlayerDatabaseDao,
    application: Application
) : ViewModel() {

    val database = dataSource

    val players = database.getAllPlayers()
}