package com.example.lostandfound.ui.owner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.databinding.ActivityOwnerSignupBinding

class OwnerSignupActivity:AppCompatActivity() {
    private lateinit var binding: ActivityOwnerSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sankarapp","here inside owner signup")
        this.binding = ActivityOwnerSignupBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
    }
}