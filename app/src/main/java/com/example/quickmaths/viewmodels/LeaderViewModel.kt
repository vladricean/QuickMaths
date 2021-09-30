package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmaths.database.PlayerDatabaseDao
import com.example.quickmaths.network.MarsApi
import com.example.quickmaths.network.MarsProperty
import kotlinx.coroutines.launch
import java.lang.Exception

enum class MarsApiStatus { LOADING, ERROR, DONE }

class LeaderViewModel(
    dataSource: PlayerDatabaseDao,
    application: Application
) : ViewModel() {

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _navigateToPlayerDetail = MutableLiveData<Long?>()
    val navigateToPlayerDetail: MutableLiveData<Long?>
            get() = _navigateToPlayerDetail

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    val database = dataSource

    val players = database.getAllPlayers()

    fun onPlayerClicked(id: Long){
        _navigateToPlayerDetail.value = id
    }

    fun onPlayerDetailNavigated() {
        _navigateToPlayerDetail.value = null
    }

    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _properties.value = MarsApi.retrofitService.getProperties()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
}

