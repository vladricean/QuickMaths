package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.io.IOException

class LeaderViewModel(
    application: Application
) : AndroidViewModel(application) {


    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _navigateToPlayerDetail = MutableLiveData<Long>()
    val navigateToPlayerDetail: LiveData<Long>
        get() = _navigateToPlayerDetail

    fun onPlayerClicked(id: Long){
        _navigateToPlayerDetail.value = id
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

  

}

