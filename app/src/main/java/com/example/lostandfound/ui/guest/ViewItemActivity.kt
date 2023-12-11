package com.example.lostandfound.ui.guest

import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityViewItemBinding
import com.google.firebase.storage.FirebaseStorage

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

                    this.binding.itemID.setText("${i.id}")
                    this.binding.itemAddress.setText("${i.address}")
                    this.binding.itemType.setText("Type: ${i.type}")
//                    this.binding.propertyCity.setText("${i.city}, ${i.postal}")
//                    this.binding.propertySpecs.setText("Specifications: ${i.specs}")
                    this.binding.itemDesciription.setText("${i.description}")
                    if (i.isClaimed)
                    {
                        this.binding.itemIsClaimed.setText("CLAIMED")
                        this.binding.itemIsClaimed.setTextColor(Color.rgb(1,100,32))
                    }
                    else
                    {
                        this.binding.itemIsClaimed.setText("UNCLAIMED")
                        this.binding.itemIsClaimed.setTextColor(Color.rgb(255,0,0))
                    }
                    this.binding.itemReporter.setText("Contact: ${i.reporter}")



                    // Assuming you have the downloadUrl from Firebase Storage
                    val downloadUrl = "gs://georgebrowncollege.appspot.com/images/image_1702266088767.jpg"

                    // Get the reference to your ImageView
                    val image : ImageView= findViewById(R.id.typeImage)

                    // Use Firebase Storage API to load the image
                    val storage = FirebaseStorage.getInstance()
                    val storageReference = storage.getReferenceFromUrl(downloadUrl)

                    // Load the image into ImageView
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        // Use the uri to set the image in ImageView
                        image.setImageURI(Uri.parse(uri.toString()))
                    }.addOnFailureListener { exception ->
                        // Handle the error
                        Log.e("FirebaseStorage", "Error getting download URL", exception)
                    }

//                    if (i.type == "House") {
//                        val imagename = "house"
//                        val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//                        this.binding.typeImage.setImageResource(res)
//                    } else if (i.type == "Condo") {
//                        val imagename = "condo"
//                        val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//                        this.binding.typeImage.setImageResource(res)
//
//                    } else if (i.type == "Apartment") {
//                        val imagename = "apartment"
//                        val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//                        this.binding.typeImage.setImageResource(res)
//
//                    } else if (i.type == "Basement") {
//                        val imagename = "basement"
//                        val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//                        this.binding.typeImage.setImageResource(res)
//                    }
                }
            }
        }
        //Leo
    }
}