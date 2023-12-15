package com.example.lostandfound.ui.owner

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.controller.AuthController
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.data.repositories.ClaimsRepository
import com.example.lostandfound.data.repositories.UserRepository
import com.example.lostandfound.databinding.ActivityOwnerHomePageBinding
import com.example.lostandfound.models.Claims
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
    private var description : String? = null
    private var address : String? = null
    private var contactNumber : String? = null

    private lateinit var claimsRepository: ClaimsRepository
    private lateinit var authController: AuthController
    private lateinit var userRepository: UserRepository


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.claims_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_showAllClaims -> {
                val intent = Intent(this@OwnerHomePageActivity, OwnerClaimsPage::class.java)
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

        binding = ActivityOwnerHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        claimsRepository = ClaimsRepository(applicationContext)
        userRepository = UserRepository(applicationContext)

        //set menu
        setSupportActionBar(this.binding.menuToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        itemID = intent.getStringExtra("EXTRA_ID")
        emailID = intent.getStringExtra("EMAIL_ID")

        Log.d("sankar","here inside owner home page")
        Log.d("sankar","${emailID}")
        Log.d("sankar","${itemID}")

        if (itemID != "") {
            for (i in caseArrayList) {
                if (itemID == i.id) {

                    Log.d("sankar","here inside owner home page for loop")

                    //adding to claims db
                    createClaims()

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
                        intent.putExtra("EMAIL_ID", emailID)
                        Log.d("sankar","here view claims button click from menu")
                        Log.d("sankar","${emailID}")
                        startActivity(intent)
                    }
                }
                }
            }


    }
    fun createClaims(){
        // to be implemented
        Log.d("sankar","here inside createClaims")
        itemID = intent.getStringExtra("EXTRA_ID")
        emailID = intent.getStringExtra("EMAIL_ID")
        description = intent.getStringExtra("DESCRIPTION")
        address = intent.getStringExtra("ADDRESS")
        contactNumber = intent.getStringExtra("CONTACTNUMBER")


        val claims = Claims(itemID.toString(),emailID.toString(),itemID.toString(),description.toString(),address.toString(),contactNumber.toString())

        claimsRepository.addClaimsToDB(claims)
    }
}