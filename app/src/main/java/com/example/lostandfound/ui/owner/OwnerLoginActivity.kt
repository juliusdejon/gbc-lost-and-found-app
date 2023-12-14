package com.example.lostandfound.ui.owner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.View.OnClickListener
import androidx.lifecycle.lifecycleScope
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.R
import com.example.lostandfound.controller.UserController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityOwnerLoginBinding
import com.example.lostandfound.ui.guest.caseArrayList
import com.example.lostandfound.ui.reporter.ReporterHomePageActivity
import com.mapbox.bindgen.CleanerService.register
import kotlinx.coroutines.launch

class OwnerLoginActivity:AppCompatActivity(), OnClickListener {
    private lateinit var binding:ActivityOwnerLoginBinding

    private var itemID : String? = null
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityOwnerLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)


        itemID = intent.getStringExtra("EXTRA_ID")

        if(itemID != ""){
            for(i in caseArrayList){
                if(itemID == i.id){
                    binding.tvClaimItemText.setText("You are trying to claim ${i.description}")
                }

            }
        }



        binding.btnLogin.setOnClickListener(this)
        binding.btnCreateNewAccount.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btnLogin -> {
                    login()
                }
                R.id.btnCreateNewAccount -> {
                    //implement register()
                }
            }
        }
    }

    private fun login(){
        // to be implemented

        Log.d("sankar","here inside login function")
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val type = "owner"

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
        }
        else {
            lifecycleScope.launch {
                val success = authController.signIn(
                    email,
                    password,
                )
                if (success) {
                    val intent = Intent(this@OwnerLoginActivity, OwnerHomePageActivity::class.java)
                    intent.putExtra("EXTRA_ID", itemID)
                    intent.putExtra("EMAIL_ID", email)
                    startActivity(intent)
                }
            }
        }

    }

    private fun register(){
        // to be implemented
    }

}