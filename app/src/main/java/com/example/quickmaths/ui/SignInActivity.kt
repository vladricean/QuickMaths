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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            signInWithGoogle(data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // it is generated
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        btn_google.setOnClickListener {
            createIntentSigninGoogle()
        }
        btn_anonymous.setOnClickListener {
            signInAnonymously()
        }
    }

    private fun signInAnonymously(){
        mAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    addUserToFirebase()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun createIntentSigninGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun signInWithGoogle(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val exception = task.exception
        if(task.isSuccessful) {
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Timber.d("firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Timber.w("Google sign in failed", e)
            }
        } else {
            Timber.w(exception.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Timber.d( "signInWithCredential:success")

                    addUserToFirebase()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Timber.w( "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun addUserToFirebase() {
        val currentUser = mAuth.currentUser
        val user = hashMapOf(
            "name" to currentUser!!.displayName,
            "score" to 0
        )
        db.collection("users").document(currentUser.uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Timber.i("User ${currentUser.displayName} has been added to firestore")
            }
            .addOnFailureListener { e ->
                Timber.w( "Error adding document", e)
            }
    }
}