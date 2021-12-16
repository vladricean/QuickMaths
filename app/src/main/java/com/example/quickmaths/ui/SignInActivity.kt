package com.example.quickmaths.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickmaths.MainActivity
import com.example.quickmaths.R
import com.example.quickmaths.databinding.ActivitySignInBinding
import com.example.quickmaths.viewmodels.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.editedText.observe(this,
        Observer { username ->
            checkIfUsernameExists(username)
        })
        viewModel.onConfirmPressed().observe(this,
        Observer {
            signInAnonymously(viewModel.editedText.value)
        })
    }

    private fun checkIfUsernameExists(username: String?) {
        db.collection("users")
            .whereEqualTo("name", username)
            .get()
            .addOnSuccessListener { documents ->
                if(documents.isEmpty){
                    btn_confirm.setEnabled(true)
                }else{
                    btn_confirm.setEnabled(false)
                }
            }
            .addOnFailureListener{ exception ->
                Timber.w("Error getting documents: ${exception}")
            }
    }

    private fun signInAnonymously(username: String?) {
        mAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addAnonymousUserToFirestore(username)
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

    private fun addAnonymousUserToFirestore(username: String?) {
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
                checkIfUserExists(username)
            }
        }
    }

    private fun checkIfUserExists(username: String?) {
        val currentUser = mAuth.currentUser
        val docRef = db.collection("users").document(currentUser!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Timber.w("Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // do nothing
            } else {
               addFreshUser(username)
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
    }
}