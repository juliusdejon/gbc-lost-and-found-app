package com.example.lostandfound.ui.reporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.lostandfound.R
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityReporterSignupBinding
import com.example.lostandfound.controller.AuthController
import com.google.firebase.auth.FirebaseAuth

class ReporterSignupActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityReporterSignupBinding
    private lateinit var userRepository: UserRepository
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityReporterSignupBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.btnSignUp.setOnClickListener(this)
        this.userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)

    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btnSignUp -> {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    val name = binding.etName.text.toString()
                    val contactNumber = binding.etContact.text.toString()
                    val type = "reporter"
                    authController.signUp(
                        email,
                        password,
                        name,
                        contactNumber,
                        type
                    )
                    finish()
                }
            }
        }
    }
}