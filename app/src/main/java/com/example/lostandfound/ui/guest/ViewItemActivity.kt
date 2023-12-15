package com.example.lostandfound.ui.guest

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityViewItemBinding
import com.example.lostandfound.ui.owner.OwnerLoginActivity
import com.google.firebase.storage.FirebaseStorage
import com.mapbox.maps.plugin.annotation.annotations
import com.squareup.picasso.Picasso
import java.io.File

class ViewItemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewItemBinding

    private var itemID : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemID = intent.getStringExtra("EXTRA_ID")

        //Leo
        if (itemID != "") {
            for (i in caseArrayList) {
                if (itemID == i.id) {

//                    this.binding.viewItemID.setText("Item ID: ${i.id}")

                    this.binding.viewItemType.setText("Type: ${i.type}")

                    this.binding.viewItemName.setText("${i.name}")

                    this.binding.viewItemDesc.setText("${i.description}")

                    // Use Firebase Storage API to load the image
                    val storageReference = FirebaseStorage.getInstance().getReference()
//                    val storageReference = storage.getReferenceFromUrl(downloadUrl)

                    var storageRef = storageReference.child("/images/${i.image}").downloadUrl
                    storageRef.addOnSuccessListener { uri ->
                        Picasso.get().load(uri.toString()).into(binding.viewItemImage)
                    }

                    this.binding.viewItemReporter.setText("Contact: ${i.reporter}")
                    this.binding.viewItemAddress.setText("Found at: ${i.address}")
                    this.binding.viewItemContactNum.setText("${i.contactNumber}")

                    Log.d("sankar","here inside viewitem")

                    if (i.isClaimed)
                    {
                        this.binding.viewItemisClaimed.setText("UNAVAILABLE")

                        this.binding.viewItemisClaimed.setTextColor(Color.rgb(255,0,0))
                        binding.viewItemContact.setEnabled(false)
                    }
                    else
                    {
                        this.binding.viewItemisClaimed.setText("AVAILABLE")
                        this.binding.viewItemisClaimed.setTextColor(Color.rgb(1,100,32))
                    }
                    // to do - button click listener for Contact to Claim
                    binding.viewItemContact.setOnClickListener{
                        Log.d("sankar","clicked on Contact to Claim")
                        var intent = Intent(this@ViewItemActivity, OwnerLoginActivity::class.java)
                         intent.putExtra("EXTRA_ID", itemID)
                        intent.putExtra("ADDRESS", i.address)
                        intent.putExtra("DESCRIPTION", i.description)
                        intent.putExtra("CONTACTNUMBER", i.contactNumber)
                        startActivity(intent)
                        //need to redirect to signin or signup and then pass along itemId
                    }
                }
            }
        }
        //Leo
    }
}