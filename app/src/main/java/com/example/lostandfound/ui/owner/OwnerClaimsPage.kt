package com.example.lostandfound.ui.owner

import ClaimsAdapter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.data.repositories.ClaimsRepository
import com.example.lostandfound.databinding.ActivityOwnerClaimsBinding
import com.example.lostandfound.databinding.ActivityOwnerHomePageBinding
import com.example.lostandfound.databinding.ActivityViewItemBinding
import com.example.lostandfound.models.Case
import com.example.lostandfound.models.Claims
import com.example.lostandfound.ui.guest.caseArrayList

class OwnerClaimsPage:AppCompatActivity() {

    private lateinit var binding: ActivityOwnerClaimsBinding
    private var itemID : String? = null
    private lateinit var claimsAdapter: ClaimsAdapter
    private lateinit var claimsRepository: ClaimsRepository

    var claimedList: MutableList<Claims> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sankar","here inside Owner claims")
        binding = ActivityOwnerClaimsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemID = intent.getStringExtra("EXTRA_ID")

        // Initialize the RecyclerView and ClaimsAdapter
        val recyclerView = binding.rvViewClaims
        recyclerView.layoutManager = LinearLayoutManager(this)
        claimsAdapter = ClaimsAdapter(mutableListOf())
        recyclerView.adapter = claimsAdapter

        // Initialize ClaimsRepository and start data retrieval
        claimsRepository = ClaimsRepository(this)
        claimsRepository.retrieveAllClaims()

        claimsRepository.allClaims.observe(this,
            androidx.lifecycle.Observer { claimsList ->
                if(claimsList != null){
                    claimedList.clear()
                    Log.d("sankar", "onResume: $claimsList")
                    claimedList.addAll(claimsList)
                    claimsAdapter.notifyDataSetChanged()
                }
            })

    }
}