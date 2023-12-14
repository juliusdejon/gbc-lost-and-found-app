package com.example.lostandfound.ui.owner

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.databinding.ActivityOwnerHomePageBinding
import com.example.lostandfound.databinding.ActivityViewItemBinding
import com.example.lostandfound.ui.guest.caseArrayList

class OwnerClaimsPage:AppCompatActivity() {

    private lateinit var binding: ActivityViewItemBinding
    private var itemID : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sankar","here inside Owner claims")
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
                    if (i.isClaimed) {
                        this.binding.itemIsClaimed.setText("CLAIMED")
                        this.binding.itemIsClaimed.setTextColor(Color.rgb(1, 100, 32))
                    } else {
                        this.binding.itemIsClaimed.setText("UNCLAIMED")
                        this.binding.itemIsClaimed.setTextColor(Color.rgb(255, 0, 0))
                    }
                    this.binding.itemReporter.setText("Contact: ${i.reporter}")


                }
            }
                }
    }
}