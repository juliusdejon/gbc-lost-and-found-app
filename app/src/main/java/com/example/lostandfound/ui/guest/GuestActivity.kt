package com.example.lostandfound.ui.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.R
import com.example.lostandfound.adapters.CaseAdapter
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.databinding.ActivityGuestBinding
import com.example.lostandfound.models.Case

class GuestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGuestBinding

    private val TAG = this.toString()
    private lateinit var caseRepository: CaseRepository

    private lateinit var caseAdapter: CaseAdapter
    private lateinit var caseArrayList: ArrayList<Case>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        caseArrayList = ArrayList()
        caseAdapter = CaseAdapter(caseArrayList)
        binding.rvProperties.layoutManager= LinearLayoutManager(this)
        binding.rvProperties.addItemDecoration(
            DividerItemDecoration(
                this.applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        this.binding.rvProperties.adapter = caseAdapter

        caseRepository = CaseRepository(applicationContext)
    }



    override fun onResume() {
        super.onResume()

        caseRepository.retrieveAllCases()

        caseRepository.allCases.observe(this, androidx.lifecycle.Observer { caseList ->
            if(caseList != null){

                Log.d(TAG, "onResume: $caseList")
                caseArrayList.addAll(caseList)
                caseAdapter.notifyDataSetChanged()
            }
        })
    }
}