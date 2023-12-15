package com.example.lostandfound.models

import java.util.UUID

data class Claims(
    var caseId: String,
    var emailId: String,
    val id : String = UUID.randomUUID().toString(),
    var description: String,
    var address: String,
    var contactNumber: String
)


