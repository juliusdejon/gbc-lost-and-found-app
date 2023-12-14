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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class OwnerHomePageActivity:AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHomePageBinding
    private  lateinit var firebaseAuth : FirebaseAuth
    private var itemID : String? = null
    private var emailID : String? = null


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
        emailID = intent.getStringExtra("EMAIL_ID")

        Log.d("sankar","here inside owner home page")
        Log.d("sankar","${emailID}")

        if (itemID != "") {
            for (i in caseArrayList) {
                if (itemID == i.id) {

                    Log.d("sankar","here inside owner home page for loop")


                    this.binding.itemAddress.setText("\nADDRESS: ${i.address}")
                    this.binding.itemType.setText("TYPE: ${i.type}")
                    this.binding.itemDesciription.setText("You are trying to claim: \n${i.description}")

                    // Use Firebase Storage API to load the image
                    val storageReference = FirebaseStorage.getInstance().getReference()
//                    val storageReference = storage.getReferenceFromUrl(downloadUrl)

                    var storageRef = storageReference.child("/images/${i.image}").downloadUrl
                    storageRef.addOnSuccessListener { uri ->
                        Picasso.get().load(uri.toString()).into(binding.viewItemImage)
                    }

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