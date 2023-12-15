package com.example.lostandfound.ui.owner

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityOwnerSignupBinding

class OwnerSignupActivity:AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityOwnerSignupBinding
    private lateinit var userRepository: UserRepository
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sankar","here inside owner signup")
        this.binding = ActivityOwnerSignupBinding.inflate(layoutInflater)
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
                    val type = "owner"
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