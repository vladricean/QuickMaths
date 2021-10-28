package com.example.quickmaths.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.util.SingleLiveEvent

class HomeViewModel : ViewModel() {

    private val onPlayPressed =
        SingleLiveEvent<Void>()
    private val onLeaderPressed =
        SingleLiveEvent<Void>()
    private val onSettingsPressed =
        SingleLiveEvent<Void>()
    private val onLogoutPressed =
        SingleLiveEvent<Void>()

    fun onPlayPressed(): LiveData<Void> {
        return onPlayPressed
    }

    fun onPlayTapped() {
        onPlayPressed.call()
    }

    fun onLeaderPressed(): LiveData<Void> {
        return onLeaderPressed
    }

    fun onLeaderTapped() {
        onLeaderPressed.call()
    }

    fun onSettingsPressed(): LiveData<Void> {
        return onSettingsPressed
    }

    fun onSettingsTapped() {
        onSettingsPressed.call()
    }

    fun onLogoutPressed(): LiveData<Void> {
        return onLogoutPressed
    }

    fun onLogoutTapped() {
        onLogoutPressed.call()
    }

}