package com.example.lostandfound.ui.reporter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.controller.CaseController
import com.example.lostandfound.controller.LocationController
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityCreateCaseBinding
import com.google.firebase.auth.FirebaseAuth


class CreateCaseActivity : AppCompatActivity() {
    private val TAG:String = "CreateCaseActivity"
    private lateinit var binding: ActivityCreateCaseBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository
    private lateinit var caseRepository: CaseRepository
    private lateinit var locationController: LocationController

    val CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCreateCaseBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
        this.caseRepository = CaseRepository(applicationContext)
        authController = AuthController(this, this.userRepository)
        locationController = LocationController(this)

        val categoryList:List<String> = listOf("Bag","Gadget","Apparel", "Accessories", "Other")

        val categoriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item, categoryList
        )

        this.binding.sType1.adapter = categoriesAdapter

        binding.btnCapture.setOnClickListener {
            val intent = Intent(this@CreateCaseActivity, CameraActivity::class.java)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }

        binding.btnCreateCase.setOnClickListener {
            if(binding.etAddress.text.isNotEmpty()) {
                locationController.getLocation(binding.etAddress.text.toString()) {
                    address ->
                    binding.etLat.setText("${address.latitude}")
                    binding.etLng.setText("${address.longitude}")
                }
            }
            CaseController().createCase(binding, categoryList, caseRepository)
            finish()
        }

        binding.btnGetCurrentLocation.setOnClickListener {
              locationController.getDeviceLocation {
                    location -> Log.d(TAG, "location:: ${location}")
                  locationController.getAddress(location) {
                      address -> Log.d(TAG, "address: ${address}")
                      binding.etAddress.setText("${address.getAddressLine(0)}")
                      binding.etLat.setText("${location.latitude}")
                      binding.etLng.setText("${location.longitude}")
                  }
                }
        }
    }

    // Override onActivityResult to handle the result from the launched activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CaseController().loadImage(requestCode, resultCode, data, binding)
    }

}