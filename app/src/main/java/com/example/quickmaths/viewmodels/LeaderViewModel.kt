package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmaths.database.PlayerDatabaseDao
import com.example.quickmaths.network.MathApi
import com.example.quickmaths.network.PlayerNetwork
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

enum class MathApiStatus { LOADING, ERROR, DONE }

class LeaderViewModel(
    dataSource: PlayerDatabaseDao,
    application: Application
) : ViewModel() {

    private val _status = MutableLiveData<MathApiStatus>()
    val status: LiveData<MathApiStatus>
        get() = _status

    private val _navigateToPlayerDetail = MutableLiveData<Long?>()
    val navigateToPlayerDetail: MutableLiveData<Long?>
            get() = _navigateToPlayerDetail

    private val _playersNetwork = MutableLiveData<List<PlayerNetwork>>()
    val playersNetwork: LiveData<List<PlayerNetwork>>
        get() = _playersNetwork

//    val database = dataSource
//
//    val players = database.getAllPlayers()

    fun onPlayerClicked(id: Long){
        _navigateToPlayerDetail.value = id
    }

    fun onPlayerDetailNavigated() {
        _navigateToPlayerDetail.value = null
    }

    init {
        getPlayersFromInternet()
    }

    private fun getPlayersFromInternet() {
        viewModelScope.launch {
            _status.value = MathApiStatus.LOADING
            try {
                _playersNetwork.value = MathApi.retrofitService.getPlayersFromNetwork()
                for (item: PlayerNetwork in playersNetwork.value!!){
                    Timber.i("${item.id.toString()} + ${item.name} \n")
                }
                _status.value = MathApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MathApiStatus.ERROR
                _playersNetwork.value = ArrayList()
            }
        }
    }
}

