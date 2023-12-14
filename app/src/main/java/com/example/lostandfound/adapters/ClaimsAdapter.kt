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

         val caseIdTextView: TextView = viewHolder.itemView.findViewById(R.id.textCaseId)
         val emailIdTextView: TextView = viewHolder.itemView.findViewById(R.id.textEmailId)
         val claimIdTextView: TextView = viewHolder.itemView.findViewById(R.id.textClaimId)

        Log.d("sankar","here inside onBindViewHolder of ClaimsAdapter")

        emailIdTextView.text="Email ID: ${claim.emailId}"
        claimIdTextView.text="Email ID: ${claim.caseId}"
    }

    override fun getItemCount(): Int {
        return claimsList.size
    }
}
