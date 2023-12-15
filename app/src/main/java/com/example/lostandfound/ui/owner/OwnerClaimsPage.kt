package com.example.lostandfound.ui.owner


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.R
import com.example.lostandfound.adapters.ClaimsAdapter
import com.example.lostandfound.adapters.ReporterCaseAdapter
import com.example.lostandfound.data.repositories.ClaimsRepository
import com.example.lostandfound.databinding.ActivityOwnerClaimsBinding
import com.example.lostandfound.databinding.ActivityOwnerHomePageBinding
import com.example.lostandfound.databinding.ActivityViewItemBinding
import com.example.lostandfound.models.Case
import com.example.lostandfound.models.Claims
import com.example.lostandfound.ui.MainActivity
import com.example.lostandfound.ui.guest.caseArrayList

class OwnerClaimsPage:AppCompatActivity() {

    private lateinit var binding: ActivityOwnerClaimsBinding
    private var itemID : String? = null
    private var emailID : String? = null
    private lateinit var claimsAdapter: ClaimsAdapter
    private lateinit var claimsRepository: ClaimsRepository

    var claimedList: MutableList<Claims> = mutableListOf()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.claims_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_showAllClaims -> {
                val intent = Intent(this, OwnerClaimsPage::class.java)
                intent.putExtra("EXTRA_ID", itemID)
                intent.putExtra("EMAIL_ID", emailID)
                Log.d("sankar","here view claims button click from menu")
                Log.d("sankar","${emailID}")
                startActivity(intent)
                return true
            }
            R.id.mi_Logout -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true

            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sankar","here inside Owner claims")
        binding = ActivityOwnerClaimsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set menu
        setSupportActionBar(this.binding.menuToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        itemID = intent.getStringExtra("EXTRA_ID")
        emailID = intent.getStringExtra("EMAIL_ID")

        binding.textViewEmail.setText("Currently logged in as: ${emailID}")
        binding.textViewUserName.setText("User type: Owner")

        // Set up Recycler View
        claimsAdapter = ClaimsAdapter(claimedList)
        binding.rvViewClaims.adapter = claimsAdapter
        binding.rvViewClaims.layoutManager= LinearLayoutManager(this)
        binding.rvViewClaims.addItemDecoration(
            DividerItemDecoration(
                this.applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        // Initialize ClaimsRepository and start data retrieval
        claimsRepository = ClaimsRepository(applicationContext)

        Log.d("sankar","trying to retrieve using ${emailID}")
        claimsRepository.retrieveClaimsByEmail(emailID.toString())

//        claimsAdapter = ClaimsAdapter(claimedList)

//        claimsRepository.allClaims.observe(this,
//            androidx.lifecycle.Observer { claimsList ->
//                if(claimsList != null){
////                    claimedList.clear()
//                    Log.d("sankar", "by email: onStart: $claimsList")
////                    claimedList.addAll(claimsList)
//
//                    claimsAdapter = ClaimsAdapter(claimsList.toMutableList())
//                    claimsAdapter.notifyDataSetChanged()
//                }
//            })

    }

    override fun onResume() {
        super.onResume()

        claimsRepository.allClaims.observe(this,
            androidx.lifecycle.Observer { claimsList ->
                if(claimsList != null){
                    claimedList.clear()
                    Log.d("sankar", "onStart: $claimsList")
                    claimedList.addAll(claimsList)

//                    claimsAdapter = ClaimsAdapter(claimsList.toMutableList())
                    claimsAdapter.notifyDataSetChanged()
                }
            })
    }
}