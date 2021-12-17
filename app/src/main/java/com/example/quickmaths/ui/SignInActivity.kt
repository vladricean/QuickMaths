package com.example.quickmaths.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.example.quickmaths.MainActivity
import com.example.quickmaths.R
import com.example.quickmaths.databinding.ActivitySignInBinding
import com.example.quickmaths.viewmodels.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        ViewTreeLifecycleOwner.set(window.decorView, this)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.editedText.observe(this,
            Observer { username ->
                viewModel.setupConfirmButtonState(username)
            })
        viewModel.onConfirmPressed().observe(this,
            Observer {
                viewModel.checkIfUsernameExists()
            })
        viewModel.onNavigateToMain().observe(this,
            Observer {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
    }
}