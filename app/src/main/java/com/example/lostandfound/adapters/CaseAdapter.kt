package com.example.lostandfound.adapters

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
        val currCountry: Case = caseList.get(position)

        val tvLine3 = holder.itemView.findViewById<TextView>(R.id.rowLayoutName)
        val tvLine4 = holder.itemView.findViewById<ImageView>(R.id.rowLayoutImage)
        val tvLine5 = holder.itemView.findViewById<TextView>(R.id.rowLayoutReporter)
        val tvLine6 = holder.itemView.findViewById<TextView>(R.id.rowLayoutType)
        val tvLine7 = holder.itemView.findViewById<TextView>(R.id.rowLayoutIsClaimed)
//

        tvLine3.setText("${currCountry.name}")


        // Use Firebase Storage API to load the image
        val storageReference = FirebaseStorage.getInstance().getReference()
//                    val storageReference = storage.getReferenceFromUrl(downloadUrl)

        var storageRef = storageReference.child("/images/${currCountry.image}").downloadUrl
        storageRef.addOnSuccessListener { uri ->
            Picasso.get().load(uri.toString()).into(tvLine4)
        }

        tvLine5.setText("Reported by: ${currCountry.reporter}")
        tvLine6.setText("${currCountry.type}")

        if (currCountry.isClaimed)
        {
            tvLine7.setText("UNAVAILABLE")
            tvLine7.setBackgroundResource(R.drawable.guest_unclaimed_button)

        }
        else
        {
            tvLine7.setText("AVAILABLE")
            tvLine7.setBackgroundResource(R.drawable.guest_claimed_button)
        }

    }
}