package com.example.lostandfound.adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.models.Claims


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
         val callButton: Button = viewHolder.itemView.findViewById<Button>(R.id.buttonCall)

        Log.d("sankar","here inside onBindViewHolder of ClaimsAdapter")

        descriptionTextView.text="Description: ${claim.description}"
        contactNumberTextView.text="Address: ${claim.contactNumber}"
        addressTextView.text="Contact Number: ${claim.address}"

        callButton.setOnClickListener(){
            Log.d("sankar","call button clicked")
            // call dialer function

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${claim.contactNumber}")
            viewHolder.itemView.context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return claimsList.size
    }

}
