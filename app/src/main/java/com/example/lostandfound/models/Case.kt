package com.example.lostandfound.models

import java.util.UUID

data class Case(
    var type: String,
    var description: String,
    var image: String,
    var reporter: String,
    var address : String,
    var isClaimed: Boolean,
    val id : String = UUID.randomUUID().toString()
) {}