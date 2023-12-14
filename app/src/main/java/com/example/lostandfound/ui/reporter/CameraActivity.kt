package com.example.lostandfound.ui.reporter

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityCameraBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class CameraActivity : AppCompatActivity() {
    private val TAG: String = "CameraActivity"
    private lateinit var binding: ActivityCameraBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository
    lateinit var cameraController: LifecycleCameraController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)


        if (hasPermissions() == false) {
            ActivityCompat.requestPermissions(this, CameraActivity.CAMERAX_PERMISSIONS, 0)
        } else {
        }
        initializeCameraController()
        binding.ivCapture.setOnClickListener {
            savePhotoToFirebaseStorage()
        }
    }


    private fun hasPermissions(): Boolean {
        return CameraActivity.CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initializeCameraController() {
        // initialize the camera controller
        cameraController = LifecycleCameraController(applicationContext)

        // This allows the activity's lifecycle to control when the camera is opened, stopped, and closed.
        cameraController.bindToLifecycle(this)

        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // associate the controller with the <PreviewView> in the xml file
        val previewView = binding.previewView
        previewView.controller = cameraController
    }

    companion object {
        // WRITE_EXTERNAL_STORAGE is only needed for Android P and below
        // Android P == Android version 9.0 == API 28
        private val CAMERAX_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }

    private fun savePhotoToFirebaseStorage() {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "capturedPhoto")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                // define what folder the images are stored in
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Snackbar.make(
                        binding.root,
                        "Saving photo failed, see console for error",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: return
                    uploadImageToFirebaseStorage(savedUri)
                }
            }
        )
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val storageReference = FirebaseStorage.getInstance().reference
        val uniqueImageName = "image_${System.currentTimeMillis()}.jpg"
        val imageRef = storageReference.child("images").child(uniqueImageName)

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val imageUrl = downloadUrl.toString()
                    Log.d("UploadImage", "Image uploaded successfully. URL: $imageUrl")
                    val returnIntent = Intent()
                    returnIntent.putExtra("capturedImage", imageUrl);
                    returnIntent.putExtra("capturedImageName", uniqueImageName)
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
            .addOnFailureListener { exception ->
                // Handle the error
                Log.e("UploadImage", "Error uploading image", exception)
            }
    }
}