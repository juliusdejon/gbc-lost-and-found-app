package com.example.lostandfound.ui.reporter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.example.lostandfound.R
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityReporterSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

class ReporterSignupActivity : AppCompatActivity(), OnClickListener {
    private val TAG = "ReporterSignupActivity"
    private lateinit var binding: ActivityReporterSignupBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityReporterSignupBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.btnSignUp.setOnClickListener(this)
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
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
                    signUp(
                        email,
                        password,
                        name,
                        contactNumber,
                        type
                    )
                }
            }
        }
    }

    fun signUp(
        email: String,
        password: String,
        name: String,
        contactNumber: String,
        type: String,
    ) {
        this.firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->

                if (task.isSuccessful){
                    //create user document with default profile info
                    val userToAdd = com.example.lostandfound.models.User(
                        name,
                        contactNumber,
                        email,
                        password,
                        type,
                    )
                    userRepository.signUp(userToAdd)

                    Log.d(TAG, "signUp: User account successfully create with email $email")
                    Toast.makeText(this@ReporterSignupActivity, "Account creation success", Toast.LENGTH_SHORT).show()
//                    saveToPrefs(email, password)
//                    goToMain()
                }else{
                    Log.d(TAG, "signUp: Unable to create user account : ${task.exception}", )
                    Toast.makeText(this@ReporterSignupActivity, "Account creation failed", Toast.LENGTH_SHORT).show()
                }
            }

    }


}