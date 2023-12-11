package com.example.lostandfound.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lostandfound.models.Case
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore
import java.lang.Exception


class CaseRepository (private val context : Context) {

    private val TAG = this.toString()

    private val db = Firebase.firestore

    private val COLLECTION_CASES = "Cases"
    private val FIELD_TYPE = "type"
    private val FIELD_DESCRIPTION = "description"
    private val FIELD_IMAGE= "image"
    private val FIELD_REPORTER = "reporter"
    private val FIELD_ISCLAIMED = "isClaimed"
    private val FIELD_ID = "id"
    private val FIELD_ADDRESS = "address"


    //    var allFavourites: MutableLiveData<List<Country>> = MutableLiveData<List<Country>>()
    var allCases : MutableLiveData<List<Case>> = MutableLiveData<List<Case>>()

    fun addCasetoDB(newCase: Case) {
        try {
            val data: MutableMap<String, Any> = HashMap();

            data[FIELD_TYPE] = newCase.type
            data[FIELD_DESCRIPTION] = newCase.description
            data[FIELD_IMAGE] = newCase.image
            data[FIELD_REPORTER] = newCase.reporter
            data[FIELD_ISCLAIMED] = newCase.isClaimed
            data[FIELD_ID] = newCase.id
            data[FIELD_ADDRESS] = newCase.address


            //for adding document to nested collection
            db.collection(COLLECTION_CASES)
                .document(newCase.reporter)
                .set(data)
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "addCasetoDB: Successfully added to Database $docRef")
                }
                .addOnFailureListener { ex ->
                    Log.e(TAG, "addCasetoDB: Exception ocurred while adding a document : $ex", )
                }


        } catch (ex: Exception) {
            Log.e(TAG, "addCasetoDB: Couldn't perform insert on Expenses collection due to exception $ex", )
        }
    }

    fun retrieveAllCases() {
        try {
            db
                .collection(COLLECTION_CASES)
                .addSnapshotListener(EventListener { result, error ->
                    if (error != null) {
                        Log.e(TAG, "retrieveAllCases: Listening to Expenses collection Failed due to error : $error")
                        return@EventListener
                    }

                    if (result != null) {
                        Log.d(TAG, "retrieveAllCases: Number of Documents retrieved : ${result.size()}")

                        val tempList: MutableList<Case> = ArrayList<Case>()

                        for (docChanges in result.documentChanges) {

                            var cType = docChanges.document.data.get("$FIELD_TYPE")
                            var cDescription = docChanges.document.data.get("$FIELD_DESCRIPTION")
                            var cImage = docChanges.document.data.get("$FIELD_IMAGE")
                            var cReporter = docChanges.document.data.get("$FIELD_REPORTER")
                            var cisClaimed = docChanges.document.data.get("$FIELD_ISCLAIMED")
                            var cID = docChanges.document.data.get("$FIELD_ID")
                            var cAddress = docChanges.document.data.get("$FIELD_ADDRESS")


                            val case = Case(
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "$cisClaimed".toBoolean(),
                                "${cID}")
                            Log.d(TAG, "retrieveAllCases: current Document : ${case}")

                            when (docChanges.type) {
                                DocumentChange.Type.ADDED -> {
                                    tempList.add(case)
                                }
                                DocumentChange.Type.MODIFIED -> {}
                                DocumentChange.Type.REMOVED -> {}
                            }
                        }
                        Log.d(TAG, "retrieveAllCases: before tempList : $tempList")
                        //replace the value in allExpenses
                        allCases.postValue(tempList)
                        Log.d(TAG, "retrieveAllCases: after add tempList : $tempList")
                    } else {
                        Log.d(TAG, "retrieveAllCases: No data in the result after retrieving")
                    }
                })

        } catch (ex: java.lang.Exception) {
            Log.e(TAG, "retrieveAllFavourites6: Unable to retrieve all expenses : $ex")
        }
    }

    fun retrieveCasesbyType(inputType : String) {
        try {
            db
                .collection(COLLECTION_CASES)
                .whereEqualTo(FIELD_TYPE, inputType)
                .addSnapshotListener(EventListener { result, error ->
                    if (error != null) {
                        Log.e(TAG, "retrieveAllCases: Listening to Expenses collection Failed due to error : $error")
                        return@EventListener
                    }

                    if (result != null) {
                        Log.d(TAG, "retrieveAllCases: Number of Documents retrieved : ${result.size()}")

                        val tempList: MutableList<Case> = ArrayList<Case>()

                        for (docChanges in result.documentChanges) {

                            var cType = docChanges.document.data.get("$FIELD_TYPE")
                            var cDescription = docChanges.document.data.get("$FIELD_DESCRIPTION")
                            var cImage = docChanges.document.data.get("$FIELD_IMAGE")
                            var cReporter = docChanges.document.data.get("$FIELD_REPORTER")
                            var cisClaimed = docChanges.document.data.get("$FIELD_ISCLAIMED")
                            var cID = docChanges.document.data.get("$FIELD_ID")
                            var cAddress = docChanges.document.data.get("$FIELD_ADDRESS")


                            val case = Case(
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "$cisClaimed".toBoolean(),
                                "${cID}")
                            Log.d(TAG, "retrieveAllCases: current Document : ${case}")

                            when (docChanges.type) {
                                DocumentChange.Type.ADDED -> {
                                    tempList.add(case)
                                }
                                DocumentChange.Type.MODIFIED -> {}
                                DocumentChange.Type.REMOVED -> {}
                            }
                        }
                        Log.d(TAG, "retrieveAllCases: before tempList : $tempList")
                        //replace the value in allExpenses
                        allCases.postValue(tempList)
                        Log.d(TAG, "retrieveAllCases: after add tempList : $tempList")
                    } else {
                        Log.d(TAG, "retrieveAllCases: No data in the result after retrieving")
                    }
                })

        } catch (ex: java.lang.Exception) {
            Log.e(TAG, "retrieveAllFavourites6: Unable to retrieve all expenses : $ex")
        }
    }

    fun retrieveCasesbyDescription(inputDesc : String) {
        try {
            db
                .collection(COLLECTION_CASES)
                .whereEqualTo(FIELD_DESCRIPTION, inputDesc)
                .addSnapshotListener(EventListener { result, error ->
                    if (error != null) {
                        Log.e(TAG, "retrieveAllCases: Listening to Expenses collection Failed due to error : $error")
                        return@EventListener
                    }

                    if (result != null) {
                        Log.d(TAG, "retrieveAllCases: Number of Documents retrieved : ${result.size()}")

                        val tempList: MutableList<Case> = ArrayList<Case>()

                        for (docChanges in result.documentChanges) {

                            var cType = docChanges.document.data.get("$FIELD_TYPE")
                            var cDescription = docChanges.document.data.get("$FIELD_DESCRIPTION")
                            var cImage = docChanges.document.data.get("$FIELD_IMAGE")
                            var cReporter = docChanges.document.data.get("$FIELD_REPORTER")
                            var cisClaimed = docChanges.document.data.get("$FIELD_ISCLAIMED")
                            var cID = docChanges.document.data.get("$FIELD_ID")
                            var cAddress = docChanges.document.data.get("$FIELD_ADDRESS")


                            val case = Case(
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "$cisClaimed".toBoolean(),
                                "${cID}")
                            Log.d(TAG, "retrieveAllCases: current Document : ${case}")

                            when (docChanges.type) {
                                DocumentChange.Type.ADDED -> {
                                    tempList.add(case)
                                }
                                DocumentChange.Type.MODIFIED -> {}
                                DocumentChange.Type.REMOVED -> {}
                            }
                        }
                        Log.d(TAG, "retrieveAllCases: before tempList : $tempList")
                        //replace the value in allExpenses
                        allCases.postValue(tempList)
                        Log.d(TAG, "retrieveAllCases: after add tempList : $tempList")
                    } else {
                        Log.d(TAG, "retrieveAllCases: No data in the result after retrieving")
                    }
                })

        } catch (ex: java.lang.Exception) {
            Log.e(TAG, "retrieveAllFavourites6: Unable to retrieve all expenses : $ex")
        }
    }

    fun removeCase(remCase : Case){
        try{
            db.collection(COLLECTION_CASES)
                .document(remCase.reporter)
                .delete()
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "removeCase: Document deleted successfully : $docRef")
                }
                .addOnFailureListener { ex ->
                    Log.e(TAG, "removeCase: Failed to delete document : $ex", )
                }
        }
        catch (ex : Exception){
            Log.e(TAG, "removeCase: Unable to delete country due to exception : $ex", )
        }
    }
}