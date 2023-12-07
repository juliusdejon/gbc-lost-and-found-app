package com.example.lostandfound.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityMainBinding
import com.example.lostandfound.ui.reporter.ReporterSignupActivity

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReport.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnReport -> {
                val intent = Intent(this@MainActivity, ReporterSignupActivity::class.java)
                startActivity(intent)
            }
        }
    }
}