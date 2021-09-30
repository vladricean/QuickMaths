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

class LeaderViewModel(
    dataSource: PlayerDatabaseDao,
    application: Application
) : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private val _navigateToPlayerDetail = MutableLiveData<Long?>()
    val navigateToPlayerDetail: MutableLiveData<Long?>
            get() = _navigateToPlayerDetail

    private val _property = MutableLiveData<MarsProperty>()
    val property: LiveData<MarsProperty>
        get() = _property

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
            try {
                _properties.value = MarsApi.retrofitService.getProperties()
                _response.value = "Success: Mars properties retrieved"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}

