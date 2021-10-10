package com.example.quickmaths.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.quickmaths.database.PlayersDatabase
import com.example.quickmaths.database.asDomaninModel
import com.example.quickmaths.domain.DomainPlayer
import com.example.quickmaths.network.DevMathNetwork
import com.example.quickmaths.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PlayersRepository(private val database: PlayersDatabase) {

    val players: LiveData<List<DomainPlayer>> = Transformations.map(database.playerDao.getAllPlayers()) {
        it.asDomaninModel()
    }

    suspend fun refreshPlayers(){
        withContext(Dispatchers.IO) {
            Timber.d("refresh players is called")
            val playerslist = DevMathNetwork.retrofitService.getPlayerslist()
            database.playerDao.insertAll(playerslist.asDatabaseModel())
        }
    }
}

