package com.example.lostandfound.ui.reporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityReporterHomePageBinding
import com.example.lostandfound.databinding.ActivityReporterLoginBinding
import com.google.firebase.auth.FirebaseAuth

class ReporterHomePageActivity : AppCompatActivity() {
    private val TAG:String = "ReporterHomePageActivity"
    private lateinit var binding: ActivityReporterHomePageBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityReporterHomePageBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)
    }
}