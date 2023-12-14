package com.example.lostandfound.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.models.Claims

class ClaimsAdapter(private var claimsList: MutableList<Claims>) :
    RecyclerView.Adapter<ClaimsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val caseIdTextView: TextView = itemView.findViewById(R.id.textCaseId)
        private val emailIdTextView: TextView = itemView.findViewById(R.id.textEmailId)
        private val claimIdTextView: TextView = itemView.findViewById(R.id.textClaimId)

        init {
//            itemView.setOnClickListener {
//                rowClickHandler(adapterPosition)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val claim = claimsList[position]

         val caseIdTextView: TextView = holder.itemView.findViewById(R.id.textCaseId)
         val emailIdTextView: TextView = holder.itemView.findViewById(R.id.textEmailId)
         val claimIdTextView: TextView = holder.itemView.findViewById(R.id.textClaimId)

        emailIdTextView.text="Email ID: ${emailIdTextView}"
        claimIdTextView.text="Email ID: ${claimIdTextView}"
    }

    override fun getItemCount(): Int {
        return claimsList.size
    }
}
