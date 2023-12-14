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

                    this.binding.viewItemID.setText("Item ID: ${i.id}")

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

                    if (i.isClaimed)
                    {
                        this.binding.viewItemisClaimed.setText("CLAIMED")
                        this.binding.viewItemisClaimed.setTextColor(Color.rgb(1,100,32))
                        binding.viewItemContact.setEnabled(false)
                    }
                    else
                    {
                        this.binding.viewItemisClaimed.setText("UNCLAIMED")
                        this.binding.viewItemisClaimed.setTextColor(Color.rgb(255,0,0))
                    }

                }
            }
        }
        //Leo
    }
}