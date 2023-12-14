package com.example.lostandfound.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.util.UUID

data class Case(
    var name: String,
    var type: String,
    var description: String,
    var image: String,
    var reporter: String,
    var address : String,
    var contactNumber: String,
    var geoPoint: GeoPoint,
    var isClaimed: Boolean,
    val id : String = UUID.randomUUID().toString()
)

object DataHolder {
    var case: Case? = null
    var claims: Claims? = null
}


