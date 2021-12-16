package com.example.quickmaths.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.util.SingleLiveEvent

class SignInViewModel: ViewModel() {
    private val onConfirmPressed =
        SingleLiveEvent<Void>()

    val editedText = MutableLiveData<String>()

    fun onConfirmPressed(): LiveData<Void> {
        return onConfirmPressed
    }

    fun onConfirmTapped() {
        onConfirmPressed.call()
    }
}