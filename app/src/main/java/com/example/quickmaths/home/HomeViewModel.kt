package com.example.quickmaths.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickmaths.SingleLiveEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val onPlayPressed = SingleLiveEvent<Void>()

    fun onPlayPressed(): LiveData<Void> {
        return onPlayPressed
    }

    fun onPlayTappet(){
        onPlayPressed.call()
    }

}