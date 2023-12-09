package com.example.lostandfound.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.models.Case

class CaseAdapter (private val caseList:MutableList<Case>) :
    RecyclerView.Adapter<CaseAdapter.UserViewHolder>() {


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
//        init {
//            itemView.findViewById<Button>(R.id.removeBtn).setOnClickListener {
//                delBtnClickHandler(adapterPosition)
//            }
//        }
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
        tvLine2.setText("${currCountry.lat}, ${currCountry.lng}") // TODO: Convert to actual address through Geocoder
        tvLine3.setText("${currCountry.description}")

//        tvLine4. TODO: Insert Image
//        if (i.type == "House") {
//            val imagename = "house"
//            val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//            this.binding.typeImage.setImageResource(res)
//        } else if (i.type == "Condo") {
//            val imagename = "condo"
//            val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//            this.binding.typeImage.setImageResource(res)
//
//        } else if (i.type == "Apartment") {
//            val imagename = "apartment"
//            val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//            this.binding.typeImage.setImageResource(res)
//
//        } else if (i.type == "Basement") {
//            val imagename = "basement"
//            val res = resources.getIdentifier(imagename, "drawable", this.packageName)
//            this.binding.typeImage.setImageResource(res)
//        }



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