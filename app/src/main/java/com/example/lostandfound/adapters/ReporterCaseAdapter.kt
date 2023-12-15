package com.example.lostandfound.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.models.Case
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ReporterCaseAdapter (private val caseList:MutableList<Case>,
                   private val rowClickHandler: (Int) -> Unit) :
    RecyclerView.Adapter<ReporterCaseAdapter.ReporterViewHolder>() {



    inner class ReporterViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        init {
            itemView.setOnClickListener{
                rowClickHandler(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporterViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_layout_guest, parent, false)
        return ReporterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return caseList.size
    }

    override fun onBindViewHolder(holder: ReporterViewHolder, position: Int) {
        // 1. Get the current user
        val currCountry: Case = caseList.get(position)

        val tvLine1 = holder.itemView.findViewById<TextView>(R.id.rowLayoutID)
//        val tvLine2 = holder.itemView.findViewById<TextView>(R.id.rowLayoutAddress)
        val tvLine3 = holder.itemView.findViewById<TextView>(R.id.rowLayoutName)
        val tvLine4 = holder.itemView.findViewById<ImageView>(R.id.rowLayoutImage)
        val tvLine5 = holder.itemView.findViewById<TextView>(R.id.rowLayoutReporter)
        val tvLine6 = holder.itemView.findViewById<TextView>(R.id.rowLayoutType)
        val tvLine7 = holder.itemView.findViewById<TextView>(R.id.rowLayoutIsClaimed)
//
//        tvLine1.setText("ID: ${currCountry.id}")

        //Address
//        tvLine2.setText("${currCountry.address}")
        //Address

        tvLine3.setText("${currCountry.name}")


        val storageReference = FirebaseStorage.getInstance().getReference("images/${currCountry.image}")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(tvLine4)

        }.addOnFailureListener { exception ->
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }



        tvLine5.setText("${currCountry.reporter}")
        tvLine6.setText("${currCountry.type}")

        if (currCountry.isClaimed)
        {
            tvLine7.setText("CLAIMED")
            tvLine7.setBackgroundResource(R.drawable.guest_claimed_button)
        }
        else
        {
            tvLine7.setText("UNCLAIMED")
            tvLine7.setBackgroundResource(R.drawable.guest_unclaimed_button)

        }

    }

}