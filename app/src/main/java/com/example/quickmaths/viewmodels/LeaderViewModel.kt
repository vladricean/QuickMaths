package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.quickmaths.database.getDatabase
import com.example.quickmaths.network.NetworkPlayer
import com.example.quickmaths.repository.PlayersRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class LeaderViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val playersRepository = PlayersRepository(getDatabase(application))

    val playerslist = playersRepository.players

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository(){
        viewModelScope.launch {
            try {
                playersRepository.refreshPlayers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException){
                if(playerslist.value.isNullOrEmpty()){
                    _eventNetworkError.value = true
                }
            }
        }
    }





  

}

