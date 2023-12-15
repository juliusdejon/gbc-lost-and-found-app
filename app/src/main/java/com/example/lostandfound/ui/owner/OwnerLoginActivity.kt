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
import com.example.lostandfound.ui.reporter.ReporterSignupActivity
import com.mapbox.bindgen.CleanerService.register
import kotlinx.coroutines.launch

class OwnerLoginActivity:AppCompatActivity(), OnClickListener {
    private lateinit var binding:ActivityOwnerLoginBinding

    private var itemID : String? = null
    private var description : String? = null
    private var address : String? = null
    private var contactNumber : String? = null
    private var homeFlag : String? = null
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityOwnerLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)


        itemID = intent.getStringExtra("EXTRA_ID")
        description = intent.getStringExtra("DESCRIPTION")
        address = intent.getStringExtra("ADDRESS")
        contactNumber = intent.getStringExtra("CONTACTNUMBER")
        homeFlag = intent.getStringExtra("HOME_FLAG")

        if(itemID != ""){
            for(i in caseArrayList){
                if(itemID == i.id){
                    binding.tvClaimItemText.setText("You are trying to claim ${i.description}")
                }

            }
        }
        else
        {
            binding.tvClaimItemText.setText("Login below")
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
                    register()
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
                    if(homeFlag == "true"){
                        Log.d("sankar","home flag is true")
                        val intent = Intent(this@OwnerLoginActivity, OwnerClaimsPage::class.java)
                        intent.putExtra("EXTRA_ID", itemID)
                        intent.putExtra("EMAIL_ID", email)
                        startActivity(intent)
                    }
                    else {
                        val intent =
                            Intent(this@OwnerLoginActivity, OwnerHomePageActivity::class.java)
                        intent.putExtra("EXTRA_ID", itemID)
                        intent.putExtra("EMAIL_ID", email)
                        intent.putExtra("ADDRESS", address)
                        intent.putExtra("DESCRIPTION", description)
                        intent.putExtra("CONTACTNUMBER", contactNumber)
                        startActivity(intent)
                    }
                }
            }
        }

    }

    private fun register(){
        // to be implemented
        val intent = Intent(this@OwnerLoginActivity, OwnerSignupActivity::class.java)
        startActivity(intent)
    }

}