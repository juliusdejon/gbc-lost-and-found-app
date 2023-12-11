package com.example.lostandfound.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.models.Case
import com.google.firebase.storage.FirebaseStorage


class CaseAdapter (private val caseList:MutableList<Case>,
                   private val rowClickHandler: (Int) -> Unit) :
    RecyclerView.Adapter<CaseAdapter.UserViewHolder>() {



    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        init {
            itemView.setOnClickListener{
                rowClickHandler(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_layout_guest, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return caseList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // 1. Get the current user
        val currCountry: Case = caseList.get(position)

        val tvLine1 = holder.itemView.findViewById<TextView>(R.id.rowLayoutID)
        val tvLine2 = holder.itemView.findViewById<TextView>(R.id.rowLayoutAddress)
        val tvLine3 = holder.itemView.findViewById<TextView>(R.id.rowLayoutDesc)
        val tvLine4 = holder.itemView.findViewById<ImageView>(R.id.rowLayoutImage)
        val tvLine5 = holder.itemView.findViewById<TextView>(R.id.rowLayoutReporter)
        val tvLine6 = holder.itemView.findViewById<TextView>(R.id.rowLayoutType)
        val tvLine7 = holder.itemView.findViewById<TextView>(R.id.rowLayoutIsClaimed)
//
        tvLine1.setText("ID: ${currCountry.id}")

        //Address
        tvLine2.setText("${currCountry.address}")
        //Address

        tvLine3.setText("${currCountry.description}")


        // Assuming you have the downloadUrl from Firebase Storage
        val downloadUrl = "gs://georgebrowncollege.appspot.com/images/image_1702265714965.jpg"

        // Get the reference to your ImageView
        //        tvLine4

        // Use Firebase Storage API to load the image
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.getReferenceFromUrl(downloadUrl)

        // Load the image into ImageView
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // Use the uri to set the image in ImageView
            tvLine4.setImageURI(uri)
        }.addOnFailureListener { exception ->
            // Handle the error
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }



        tvLine5.setText("${currCountry.reporter}")
        tvLine6.setText("${currCountry.type}")

        if (currCountry.isClaimed)
        {
            tvLine7.setText("CLAIMED")
            tvLine7.setTextColor(Color.rgb(1,100,32))
        }
        else
        {
            tvLine7.setText("NOT CLAIMED")
            tvLine7.setTextColor(Color.rgb(255,0,0))
        }

    }

}