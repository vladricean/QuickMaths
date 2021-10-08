package com.example.quickmaths.viewmodelsfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.viewmodels.LeaderViewModel

class LeaderViewModelFactory(
    private val application: Application
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderViewModel::class.java)) {
            return LeaderViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}