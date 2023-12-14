package com.example.lostandfound.controller

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.databinding.ActivityCreateCaseBinding
import com.example.lostandfound.databinding.ActivityUpdateCaseBinding
import com.example.lostandfound.models.Case
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.squareup.picasso.Picasso

class CaseController {
    private val TAG: String = "CaseController"
    val CAMERA_REQUEST_CODE = 1

    fun loadImage(requestCode: Int, resultCode: Int, data: Intent?, binding: Any) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Handle the result from YourNextActivity
                val capturedImage = data?.getStringExtra("capturedImage")?.toUri()
                val captureImageName = data?.getStringExtra("capturedImageName")
                Log.d(TAG, "${capturedImage}")

                // Check the type of binding and load the image accordingly
                when (binding) {
                    is ActivityCreateCaseBinding -> {
                        Picasso.get().load(capturedImage).rotate(90f).into(binding.btnCapture)
                        binding.tvImageName.text = captureImageName
                    }
                    is ActivityUpdateCaseBinding -> {
                        Picasso.get().load(capturedImage).rotate(90f).into(binding.btnCapture)
                        binding.tvImageName.text = captureImageName
                    }
                }

            } else {
                // Handle the case where the user canceled or the operation failed
            }
        }
    }


    fun createCase(binding: ActivityCreateCaseBinding, categoryList: List<String>, caseRepository: CaseRepository) {
        val imageName = binding.tvImageName.text.toString()
        val name = binding.etName.text.toString()
        val category = categoryList[binding.sType1.selectedItemPosition]
        val description = binding.etDescription.text.toString()
        val addr = binding.etAddress.text.toString()
        val contactNumber = binding.etContactNumber.text.toString()
        val lat:Double = if (binding.etLat.text.toString().isNotEmpty()) {
            binding.etLat.text.toString().toDouble()
        } else {
            0.0
        }
        val lng:Double = if (binding.etLng.text.toString().isNotEmpty()) {
            binding.etLng.text.toString().toDouble()
        } else {
            0.0
        }
        val claimed = binding.cClaimed.isChecked

        val auth = FirebaseAuth.getInstance();
        val currentUserEmail = auth.currentUser?.email.toString()

        val geoPoint = GeoPoint(lat, lng)
        val newCase = Case(
            name,
            category,
            description,
            imageName,
            currentUserEmail,
            addr,
            contactNumber,
            geoPoint,
            claimed,
        )

        caseRepository.addCase(newCase)
    }

    fun updateCase(caseId: String, binding: ActivityUpdateCaseBinding, categoryList: List<String>, caseRepository: CaseRepository) {
        val name = binding.etName.text.toString()
        val imageName = binding.tvImageName.text.toString()
        val category = categoryList[binding.sType.selectedItemPosition]
        val description = binding.etDescription.text.toString()
        val addr = binding.etAddress.text.toString()
        val claimed = binding.cClaimed.isChecked
        val contactNumber = binding.etContactNumber.text.toString()
        val lat:Double = binding.etLat.text.toString().toDouble()
        val lng:Double = binding.etLng.text.toString().toDouble()

        val auth = FirebaseAuth.getInstance();
        val currentUserEmail = auth.currentUser?.email.toString()


        val geoPoint = GeoPoint(lat, lng)
        val case = Case(
            name,
            category,
            description,
            imageName,
            currentUserEmail,
            addr,
            contactNumber,
            geoPoint,
            claimed,
            caseId,
        )

        caseRepository.updateCase(case)
    }

    fun deleteCase(caseId: String, caseRepository: CaseRepository) {
        caseRepository.deleteCase(caseId)
    }

}