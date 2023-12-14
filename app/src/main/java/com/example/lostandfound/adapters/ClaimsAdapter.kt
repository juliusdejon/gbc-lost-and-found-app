package com.example.lostandfound.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.models.Claims
import com.google.android.play.integrity.internal.c
import kotlinx.coroutines.NonDisposableHandle.parent

class ClaimsAdapter(private var claimsList: MutableList<Claims>) :
    RecyclerView.Adapter<ClaimsAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {


        init {
//            itemView.setOnClickListener {
//                rowClickHandler(adapterPosition)
//            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val claim = claimsList[position]

         val descriptionTextView: TextView = viewHolder.itemView.findViewById(R.id.textDescription)
         val contactNumberTextView: TextView = viewHolder.itemView.findViewById(R.id.textContactNumber)
         val addressTextView: TextView = viewHolder.itemView.findViewById(R.id.textAddress)

        Log.d("sankar","here inside onBindViewHolder of ClaimsAdapter")

        descriptionTextView.text="Description: ${claim.description}"
        contactNumberTextView.text="Address: ${claim.contactNumber}"
        addressTextView.text="Contact Number: ${claim.address}"
    }

    override fun getItemCount(): Int {
        return claimsList.size
    }
}
