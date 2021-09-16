package com.example.quickmaths.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.util.SingleLiveEvent

class FirstTimeViewModel : ViewModel() {
    private val onNavigateToHomeFragment = SingleLiveEvent<Void>()

    fun onNavigateToHomeFragment(): LiveData<Void>{
        return onNavigateToHomeFragment
    }

    fun onRegisterPressed(){
        onNavigateToHomeFragment.call()
    }
}