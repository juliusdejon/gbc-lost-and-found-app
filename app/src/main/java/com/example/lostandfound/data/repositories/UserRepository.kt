package com.example.lostandfound.data.repositories

import android.content.Context
import android.util.Log
import com.example.lostandfound.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UserRepository(private val context: Context) {
    private val TAG = this.toString()
    private val db = Firebase.firestore

    private val COLLECTION_USERS = "Users"
    private val FIELD_EMAIL = "email"
    private val FIELD_PASSWORD = "password"
    private val FIELD_NAME = "name"
    private val FIELD_PHONE = "contactNumber"
    private val FIELD_TYPE = "type"

    fun signUp(newUser : User){
        try{
            val data : MutableMap<String, Any> = HashMap()

            data[FIELD_EMAIL] = newUser.email
            data[FIELD_PASSWORD] = newUser.password
            data[FIELD_PHONE] = newUser.contactNumber
            data[FIELD_NAME] = newUser.name
            data[FIELD_TYPE] = newUser.type

            db.collection(COLLECTION_USERS)
                .document("${newUser.email}-${newUser.type}")
                .set(data)
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "signUp: User document successfully created with ID $docRef")
                }
                .addOnFailureListener { ex ->
                    Log.e(TAG, "signUp: Unable to create user document due to exception : $ex", )
                }

        }catch (ex : Exception){
            Log.e(TAG, "signUp: Couldn't add user document $ex", )
        }
    }

}