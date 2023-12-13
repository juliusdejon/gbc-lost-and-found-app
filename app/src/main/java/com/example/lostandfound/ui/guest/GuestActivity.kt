package com.example.lostandfound.ui.guest

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.R
import com.example.lostandfound.adapters.CaseAdapter
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.databinding.ActivityGuestBinding
import com.example.lostandfound.models.Case
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

var caseArrayList: ArrayList<Case> = ArrayList()

class GuestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGuestBinding

    private val TAG = "GuestActivity"
    private lateinit var caseRepository: CaseRepository
    private lateinit var caseAdapter: CaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val search = intent.getStringExtra("search")
        binding.etSearch.setText(search)

        caseAdapter = CaseAdapter(caseArrayList, { pos -> rowClicked(pos) })
        binding.rvProperties.layoutManager= LinearLayoutManager(this)
        binding.rvProperties.addItemDecoration(
            DividerItemDecoration(
                this.applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        this.binding.rvProperties.adapter = caseAdapter

        caseRepository = CaseRepository(applicationContext)



//Search Button
        binding.btnSearch.setOnClickListener {
            val searchFromUI = binding.etSearch.text.toString()
            caseArrayList.clear()
            caseRepository.retrieveCasesbyDescription(searchFromUI)
        }
        //Search Button//

        //Radio Filter Handler
        binding.radioALL.setOnClickListener {
            caseArrayList.clear()
            caseRepository.retrieveAllCases()
        }

        binding.radioBag.setOnClickListener {
            caseRepository.retrieveCasesbyType("Bag")
        }

        binding.radioGadget.setOnClickListener {
            caseRepository.retrieveCasesbyType("Gadget")
        }

        binding.radioClothes.setOnClickListener {
            caseRepository.retrieveCasesbyType("Clothes")
        }
        //Radio Filter Handler


        binding.mapViewBtn.setOnClickListener {
            var intent = Intent(this@GuestActivity, MapViewActivity::class.java)
//            intent.putExtra("EXTRA_ID", searchedProperties[position].id)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        caseRepository.retrieveAllCases()

        caseRepository.allCases.observe(this,
            androidx.lifecycle.Observer { caseList ->
            if(caseList != null){
                caseArrayList.clear()
                Log.d(TAG, "onResume: $caseList")
                caseArrayList.addAll(caseList)
                caseAdapter.notifyDataSetChanged()
            }
        })
    }

    fun rowClicked(position: Int) {
        if(caseArrayList.size > 0) {
            var intent = Intent(this@GuestActivity, ViewItemActivity::class.java)
            intent.putExtra("EXTRA_ID", caseArrayList[position].id)
            startActivity(intent)
//            val snackbar = Snackbar.make(binding.root, "Clicked Row : $position, ${caseArrayList[position].description}", Snackbar.LENGTH_LONG).show()
        }
    }
}

