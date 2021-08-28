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
    private val onLeaderPressed = SingleLiveEvent<Void>()
    private val onSettingsPressed = SingleLiveEvent<Void>()

    fun onPlayPressed(): LiveData<Void> {
        return onPlayPressed
    }

    fun onPlayTapped(){
        onPlayPressed.call()
    }

    fun onLeaderPressed(): LiveData<Void>{
        return onLeaderPressed
    }

    fun onLeaderTapped(){
        onLeaderPressed.call()
    }

    fun onSettingsPressed(): LiveData<Void>{
        return onSettingsPressed
    }

    fun onSettingsTapped(){
        onSettingsPressed.call()
    }

}