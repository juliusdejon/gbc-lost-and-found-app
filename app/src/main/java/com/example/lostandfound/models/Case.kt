package com.example.lostandfound.models

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
    var lat: Double,
    var lng: Double,
    var isClaimed: Boolean,
    val id : String = UUID.randomUUID().toString()
) : Serializable {}