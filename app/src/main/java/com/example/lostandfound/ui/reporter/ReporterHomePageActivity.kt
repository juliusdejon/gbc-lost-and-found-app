package com.example.lostandfound.ui.reporter

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityCreateCaseBinding
import com.example.lostandfound.databinding.ActivityReporterHomePageBinding
import com.example.lostandfound.databinding.ActivityReporterLoginBinding
import com.example.lostandfound.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class ReporterHomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReporterHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityReporterHomePageBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.btnCreateCase.setOnClickListener {
            val intent = Intent(this@ReporterHomePageActivity, CreateCaseActivity::class.java)
            startActivity(intent)
        }
    }
}