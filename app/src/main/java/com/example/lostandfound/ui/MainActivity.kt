package com.example.lostandfound.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat.startActivity
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityMainBinding
import com.example.lostandfound.ui.guest.GuestActivity
import com.example.lostandfound.ui.reporter.ReporterLoginActivity

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReport.setOnClickListener(this)

        //open intent Guest Activity
//        binding.btnOpenMapView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnReport -> {
                Log.d("APP", "onClick: ")
                val intent = Intent(this@MainActivity, ReporterLoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}