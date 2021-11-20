package com.example.quickmaths.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LeaderViewModel(
    application: Application
) : AndroidViewModel(application) {


    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _navigateToPlayerDetail = MutableLiveData<String>()
    val navigateToPlayerDetail: LiveData<String>
        get() = _navigateToPlayerDetail

    fun onPlayerClicked(id: String){
        _navigateToPlayerDetail.value = id
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

  

}

