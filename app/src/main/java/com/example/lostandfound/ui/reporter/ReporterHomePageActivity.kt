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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.R
import com.example.lostandfound.adapters.CaseAdapter
import com.example.lostandfound.adapters.ReporterCaseAdapter
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityCreateCaseBinding
import com.example.lostandfound.databinding.ActivityReporterHomePageBinding
import com.example.lostandfound.databinding.ActivityReporterLoginBinding
import com.example.lostandfound.models.Case
import com.example.lostandfound.models.DataHolder
import com.example.lostandfound.ui.MainActivity
import com.example.lostandfound.ui.guest.caseArrayList
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class ReporterHomePageActivity : AppCompatActivity() {
    var casesList: MutableList<Case> = mutableListOf()

    private val TAG: String = "ReporterHomePageActivity"
    private lateinit var binding: ActivityReporterHomePageBinding
    private lateinit var caseAdapter: ReporterCaseAdapter
    private lateinit var caseRepository: CaseRepository
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityReporterHomePageBinding.inflate(layoutInflater)
        setContentView(this.binding.root)



        caseAdapter = ReporterCaseAdapter(casesList, { pos -> rowClicked(pos) })
        binding.rvCases.layoutManager= LinearLayoutManager(this)
        binding.rvCases.addItemDecoration(
            DividerItemDecoration(
                this.applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        this.binding.rvCases.adapter = caseAdapter


        caseRepository = CaseRepository(applicationContext)
        userRepository = UserRepository(applicationContext)
        authController = AuthController(this, this.userRepository)

        binding.btnCreateCase.setOnClickListener {
            val intent = Intent(this@ReporterHomePageActivity, CreateCaseActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogout.setOnClickListener {
            authController.signOut()
            finish()
        }
    }
    private fun rowClicked(position: Int) {
        val intent = Intent(this@ReporterHomePageActivity, UpdateCaseActivity::class.java)
        DataHolder.case = casesList[position]
        intent.putExtra("capturedImage", "${casesList[position].image}")
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val auth = FirebaseAuth.getInstance();
        val currentUserEmail = auth.currentUser?.email
        Log.d(TAG, "${currentUserEmail}")
        caseRepository.retrieveCasesByEmail(currentUserEmail.toString())

        caseRepository.allCases.observe(this,
            androidx.lifecycle.Observer { caseList ->
                if(caseList != null){
                    casesList.clear()
                    Log.d(TAG, "onResume: $caseList")
                    casesList.addAll(caseList)
                    caseAdapter.notifyDataSetChanged()
                }
            })

    }

}