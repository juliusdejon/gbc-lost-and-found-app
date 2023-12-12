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
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityCreateCaseBinding
import com.example.lostandfound.databinding.ActivityUpdateCaseBinding
import com.example.lostandfound.models.Case
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

    val CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityUpdateCaseBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.firebaseAuth = FirebaseAuth.getInstance()
        this.userRepository = UserRepository(applicationContext)
        this.caseRepository = CaseRepository(applicationContext)
        authController = AuthController(this, this.userRepository)

        val categoryList:List<String> = listOf("Bag","Gadget","Clothes")

        val categoriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item, categoryList
        )

        this.binding.sType.adapter = categoriesAdapter

        val case = intent.getSerializableExtra("case") as? Case
        Log.d(TAG, "${case}")
        if (case != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("images/${case.image}")
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get().load(uri).into(binding.btnCapture)
            }

            binding.etName.setText(case.name.toString())
            binding.etContactNumber.setText(case.contactNumber.toString())
            binding.sType.setSelection(categoryList.indexOf(case.type))
            binding.etDescription.setText(case.description.toString())
            binding.etAddress.setText(case.address.toString())
            binding.cClaimed.isChecked = case.isClaimed


            binding.btnUpdateCase.setOnClickListener {
                CaseController().updateCase(case.id, binding, categoryList, caseRepository)
                finish()
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