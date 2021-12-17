package com.example.quickmaths.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.enums.ConfirmButtonState
import com.example.quickmaths.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInViewModel: ViewModel() {

    private val db = Firebase.firestore

    private val onConfirmPressed =
        SingleLiveEvent<Void>()

    private var _confirmButtonState = MutableLiveData<ConfirmButtonState>()
        val confirmButtonState: LiveData<ConfirmButtonState>
        get() = _confirmButtonState

    val editedText = MutableLiveData<String>()

    fun onConfirmPressed(): LiveData<Void> {
        return onConfirmPressed
    }

    fun onConfirmTapped() {
        onConfirmPressed.call()
    }

    fun setupConfirmButtonState(username: String?) {
        if(usernameIsValid(username)) {
            db.collection("users")
                .whereEqualTo("name", username)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        _confirmButtonState.value = ConfirmButtonState.ENABLED
                    } else {
                        _confirmButtonState.value = ConfirmButtonState.DISABLED
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.w("Error getting documents: ${exception}")
                }
        } else{
            _confirmButtonState.value = ConfirmButtonState.DISABLED
        }
    }

    private fun usernameIsValid(username: String?): Boolean {
        if(username?.length ?: 0 < 3) {
            return false
        }
        if(username.isNullOrEmpty()){
            return false
        }
        return true
    }
}