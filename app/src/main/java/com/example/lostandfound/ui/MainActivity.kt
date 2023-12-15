package com.example.lostandfound.ui

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityMainBinding
import com.example.lostandfound.ui.guest.GuestActivity
import com.example.lostandfound.ui.owner.OwnerClaimsPage
import com.example.lostandfound.ui.owner.OwnerLoginActivity
import com.example.lostandfound.ui.owner.OwnerSignupActivity
import com.example.lostandfound.ui.reporter.ReporterLoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), OnClickListener {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReport.setOnClickListener(this)

        binding.btnOwner.setOnClickListener(this)

        // open intent Guest Activity
        // binding.btnOpenMapView.setOnClickListener(this)
        binding.btnBrowser.setOnClickListener(this)

        // Click listener for Owner
//        binding.btnOwner.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOwner -> {
                Log.d("sankar","clicked on btnOwner")
                var intent = Intent(this, OwnerLoginActivity::class.java)
                intent.putExtra("HOME_FLAG", "true")
                startActivity(intent)
            }

            R.id.btnReport -> {
                Log.d("APP", "onClick: ")
                val intent = Intent(this@MainActivity, ReporterLoginActivity::class.java)
                startActivity(intent)
            }

            R.id.btnBrowser -> {
                val intent = Intent(this@MainActivity, GuestActivity::class.java)
                val search = binding.etSearch.text.toString()
                intent.putExtra("search", search)
                startActivity(intent)
            }
        }
    }
}