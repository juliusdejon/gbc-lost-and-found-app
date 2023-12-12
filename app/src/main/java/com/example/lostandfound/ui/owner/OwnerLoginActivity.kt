package com.example.lostandfound.ui.owner

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.View.OnClickListener
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityOwnerLoginBinding
import com.example.lostandfound.databinding.ActivityReporterLoginBinding
import com.mapbox.bindgen.CleanerService.register

class OwnerLoginActivity:AppCompatActivity(), OnClickListener {
    private lateinit var binding:ActivityOwnerLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityOwnerLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.btnCreateNewAccount.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btnLogin -> {
                   //implement login()
                }
                R.id.btnCreateNewAccount -> {
                    //implement register()
                }
            }
        }
    }

    private fun login(){
        // to be implemented
    }

    private fun register(){
        // to be implemented
    }

}