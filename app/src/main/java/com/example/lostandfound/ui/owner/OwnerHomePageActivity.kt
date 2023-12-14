package com.example.lostandfound.ui.owner

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityOwnerHomePageBinding
import com.example.lostandfound.ui.MainActivity
import com.example.lostandfound.ui.guest.caseArrayList

class OwnerHomePageActivity:AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHomePageBinding
    private var itemID : String? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.claims_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_showAllClaims -> {
                val intent = Intent(this, OwnerClaimsPage::class.java)
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

        binding = ActivityOwnerHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set menu
        setSupportActionBar(this.binding.menuToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        itemID = intent.getStringExtra("EXTRA_ID")

        Log.d("sankar","here inside owner home page")

        if (itemID != "") {
            for (i in caseArrayList) {
                if (itemID == i.id) {

                    Log.d("sankar","here inside owner home page for loop")


                    this.binding.itemID.setText("You have claimed: ${i.id}")
                    this.binding.itemAddress.setText("${i.address}")
                    this.binding.itemType.setText("Type: ${i.type}")
//                    this.binding.propertyCity.setText("${i.city}, ${i.postal}")
//                    this.binding.propertySpecs.setText("Specifications: ${i.specs}")
                    this.binding.itemDesciription.setText("You have claimed: ${i.description}")

                    binding.btnViewClaims.setOnClickListener{
                        val intent = Intent(this@OwnerHomePageActivity, OwnerClaimsPage::class.java)
                        intent.putExtra("EXTRA_ID", itemID)
                        startActivity(intent)
                    }
                }
                }
            }
    }
}