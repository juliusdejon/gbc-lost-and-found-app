package com.example.lostandfound.ui.reporter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityReporterLoginBinding
import com.example.lostandfound.databinding.ActivityReporterSignupBinding
import com.example.lostandfound.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import kotlin.math.sign

class ReporterLoginActivity : AppCompatActivity(), OnClickListener {
    private val TAG = "ReporterLoginActivity"
    private lateinit var binding: ActivityReporterLoginBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityReporterLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.btnCreateNewAccount.setOnClickListener(this)
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btnLogin -> {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    val type = "reporter"
                    authController.signIn(
                        email,
                        password,
                    )
                }
                R.id.btnCreateNewAccount -> {
                    val intent = Intent(this@ReporterLoginActivity, ReporterSignupActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }



    private fun goToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

}