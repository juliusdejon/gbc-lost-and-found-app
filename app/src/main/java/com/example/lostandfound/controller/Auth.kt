package com.example.lostandfound.controller

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.models.User
import com.google.firebase.auth.FirebaseAuth

class Auth (var activity: Activity, var userRepository: UserRepository){
    private val TAG: String = "Auth"
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(
        email: String,
        password: String,
        name: String,
        contactNumber: String,
        type: String
    ) {
        this.firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity){task ->

                if (task.isSuccessful){
                    //create user document with default profile info
                    val userToAdd = User(
                        name,
                        contactNumber,
                        email,
                        password,
                        type,
                    )
                    userRepository.signUp(userToAdd)
                    Log.d(TAG, "signUp: User account successfully create with email $email")
                    Toast.makeText(activity, "Account creation success", Toast.LENGTH_SHORT).show()
                }else{
                    Log.d(TAG, "signUp: Unable to create user account : ${task.exception}", )
                    Toast.makeText(activity, "Account creation failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}