package com.example.quickmaths.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickmaths.enums.ConfirmButtonState
import com.example.quickmaths.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class SignInViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    private var _confirmButtonState = MutableLiveData<ConfirmButtonState>()
    val confirmButtonState: LiveData<ConfirmButtonState>
        get() = _confirmButtonState

    val editedText = MutableLiveData<String>()

    private val onNavigateToMain =
        SingleLiveEvent<Void>()

    fun onNavigateToMain(): LiveData<Void> {
        return onNavigateToMain
    }

    private val onConfirmPressed =
        SingleLiveEvent<Void>()

    fun onConfirmPressed(): LiveData<Void> {
        return onConfirmPressed
    }

    fun onConfirmTapped() {
        onConfirmPressed.call()
    }

    fun setupConfirmButtonState(username: String?) {
        if (usernameIsValid(username)) {
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
        } else {
            _confirmButtonState.value = ConfirmButtonState.DISABLED
        }
    }

    private fun usernameIsValid(username: String?): Boolean {
        if (username?.length ?: 0 < 3) {
            return false
        }
        if (username.isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun checkIfUsernameExists(username: String? = editedText.value) {
        _confirmButtonState.value = ConfirmButtonState.DISABLED
        db.collection("users")
            .whereEqualTo("name", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    signInAnonymously(username)
                } else {
                    _confirmButtonState.value = ConfirmButtonState.DISABLED
                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents: ${exception}")
            }
    }

    private fun signInAnonymously(username: String?) {
        mAuth.signInAnonymously()
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addFreshUser(username)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.e("Authentication failed.")
                }
            }
    }

    private fun addFreshUser(username: String?) {
        val user = hashMapOf(
            "name" to username,
            "score" to 0
        )
        val currentUser = mAuth.currentUser
        db.collection("users").document(currentUser!!.uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Timber.i("User ${currentUser.displayName} has been added to firestore")
            }
            .addOnFailureListener { e ->
                Timber.w("Error adding document", e)
            }
        onNavigateToMain.call()
    }
}