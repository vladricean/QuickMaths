package com.example.quickmaths.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _navigateToPlay = MutableLiveData<Boolean>()
    val navigateToPlay: LiveData<Boolean>
        get() = _navigateToPlay

    fun onPlayClicked(){
        _navigateToPlay.value = true
    }

    fun onNavigatedToPlay(){
        _navigateToPlay.value = false
    }
}