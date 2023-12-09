package com.example.lostandfound.ui.reporter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.lifecycleScope
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.controller.UserController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityReporterLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ReporterLoginActivity : AppCompatActivity(), OnClickListener {
    private val TAG:String = "ReporterLoginActivity"
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
                    login()
                }
                R.id.btnCreateNewAccount -> {
                    register()
                }
            }
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val type = "reporter"

        val (isValid, message) = UserController().validateEmailAndPassword(
            email,
            password
        )

        if (!isValid) {
            if (message.contains("Email")) {
                binding.etEmail.setError(message)
            }
            if (message.contains("Password")) {
                binding.etPassword.setError(message)
            }
        } else {
            lifecycleScope.launch {
                val success = authController.signIn(
                    email,
                    password,
                )
                if (success) {
                    val intent = Intent(this@ReporterLoginActivity, ReporterHomePageActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun register() {
        val intent = Intent(this@ReporterLoginActivity, ReporterSignupActivity::class.java)
        startActivity(intent)
    }

}