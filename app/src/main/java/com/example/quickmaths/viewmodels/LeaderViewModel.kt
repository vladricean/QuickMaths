package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.quickmaths.network.NetworkPlayer
import kotlinx.coroutines.launch
import java.lang.Exception

class LeaderViewModel(
    application: Application
) : ViewModel() {



    private val _navigateToPlayerDetail = MutableLiveData<Long?>()
    val navigateToPlayerDetail: MutableLiveData<Long?>
        get() = _navigateToPlayerDetail

    fun onPlayerClicked(id: Long) {
        _navigateToPlayerDetail.value = id
    }

    fun onPlayerDetailNavigated() {
        _navigateToPlayerDetail.value = null
    }



  

}

