package com.example.lostandfound.controller

class UserController(){
    fun validateEmailAndPassword(email: String, password: String): Pair<Boolean, String> {
        if (email.isNullOrEmpty()) return false to "Email is required"
        if (password.isNullOrEmpty()) return false to "Password is required"
        return true to "Validation successful"
    }
}