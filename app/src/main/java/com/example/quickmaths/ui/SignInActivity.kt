package com.example.quickmaths.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.quickmaths.MainActivity
import com.example.quickmaths.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        signInAnonymously()
    }

    private fun signInAnonymously() {
        mAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    onInitializeNextAnonymousUser()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun onInitializeNextAnonymousUser() {
        db.collection("extra").document("userscount")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Timber.i("cnt: ${document.get("usrcnt")}")
                    val anonymousNumber = document.get("usrcnt").toString().toInt() + 1
                    addAnonymousUserToFirestore(anonymousNumber.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }
    }

    private fun updateAnonynousNumberOnFirestore(anonymousNumber: Int) {
        val data = hashMapOf("usrcnt" to anonymousNumber)
        db.collection("extra").document("userscount")
            .set(data, SetOptions.merge())
    }

    private fun addAnonymousUserToFirestore(anonymousNumber: String) {
        val currentUser = mAuth.currentUser
        val username = "Anonymous${anonymousNumber}"
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.w("Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // Do nothing
            } else {
                checkIfUserExists(username, anonymousNumber)
            }
        }
    }

    private fun checkIfUserExists(username: String, anonymousNumber: String) {
        val currentUser = mAuth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.w("Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // Do nothing
            } else {
                addFreshUser(username, anonymousNumber)
            }
        }
    }

    private fun addFreshUser(username: String, anonymousNumber: String) {
        val user = hashMapOf(
            "name" to username,
            "score" to 0
        )
        val currentUser = mAuth.currentUser
        db.collection("users").document(currentUser!!.uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Timber.i("User ${currentUser.displayName} has been added to firestore")
                updateAnonynousNumberOnFirestore(anonymousNumber.toInt())
            }
            .addOnFailureListener { e ->
                Timber.w("Error adding document", e)
            }
    }
}