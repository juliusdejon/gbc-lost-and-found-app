package com.example.lostandfound.ui.reporter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.view.get
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.controller.CaseController
import com.example.lostandfound.controller.LocationController
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityCreateCaseBinding
import com.example.lostandfound.databinding.ActivityUpdateCaseBinding
import com.example.lostandfound.models.Case
import com.example.lostandfound.models.DataHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class UpdateCaseActivity : AppCompatActivity() {
    private val TAG:String = "UpdateCaseActivity"
    private lateinit var binding: ActivityUpdateCaseBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository
    private lateinit var caseRepository: CaseRepository
    private lateinit var locationController: LocationController

    val CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityUpdateCaseBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
        this.caseRepository = CaseRepository(applicationContext)
        authController = AuthController(this, this.userRepository)
        locationController = LocationController(this)


        val categoryList:List<String> = listOf("Bag","Gadget","Clothes")

        val categoriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item, categoryList
        )

        this.binding.sType.adapter = categoriesAdapter

        val case = DataHolder.case
        Log.d(TAG, "case1:${case}")
        if (case != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("images/${case.image}")
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(binding.btnCapture)
            }

            binding.tvImageName.setText(case.image)
            binding.etName.setText(case.name.toString())
            binding.etContactNumber.setText(case.contactNumber.toString())
            binding.sType.setSelection(categoryList.indexOf(case.type))
            binding.etDescription.setText(case.description.toString())
            binding.etAddress.setText(case.address.toString())
            binding.etLat.setText(case.geoPoint.latitude.toString())
            binding.etLng.setText(case.geoPoint.longitude.toString())
            binding.cClaimed.isChecked = case.isClaimed

            binding.btnUpdateCase.setOnClickListener {
                if(binding.etAddress.text.isNotEmpty()) {
                    locationController.getLocation(binding.etAddress.text.toString()) {
                            address ->
                        binding.etLat.setText("${address.latitude}")
                        binding.etLng.setText("${address.longitude}")
                    }
                }

                CaseController().updateCase(case.id, binding, categoryList, caseRepository)
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
            binding.btnDeleteCase.setOnClickListener {
                CaseController().deleteCase(case.id, caseRepository)
                finish()
            }
            binding.btnCapture.setOnClickListener {
                val intent = Intent(this@UpdateCaseActivity, CameraActivity::class.java)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
        }
    }

    // Override onActivityResult to handle the result from the launched activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CaseController().loadImage(requestCode, resultCode, data, binding)
    }

}