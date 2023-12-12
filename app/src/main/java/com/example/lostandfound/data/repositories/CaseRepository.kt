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
    private val FIELD_LAT = "lat"
    private val FIELD_LNG = "lng"
    private val FIELD_NAME = "name"
    private val FIELD_CONTACT_NUMBER = "contactNumber"

    //    var allFavourites: MutableLiveData<List<Country>> = MutableLiveData<List<Country>>()
    var allCases : MutableLiveData<List<Case>> = MutableLiveData<List<Case>>()

    fun addCase(newCase: Case) {
        try {
            val data: MutableMap<String, Any> = HashMap();

            data[FIELD_NAME] = newCase.name
            data[FIELD_TYPE] = newCase.type
            data[FIELD_DESCRIPTION] = newCase.description
            data[FIELD_IMAGE] = newCase.image
            data[FIELD_REPORTER] = newCase.reporter
            data[FIELD_ISCLAIMED] = newCase.isClaimed
            data[FIELD_ID] = newCase.id
            data[FIELD_ADDRESS] = newCase.address
            data[FIELD_LAT] = newCase.lat
            data[FIELD_LNG] = newCase.lng
            data[FIELD_CONTACT_NUMBER] = newCase.contactNumber


            //for adding document to nested collection
            db.collection(COLLECTION_CASES)
                .document(newCase.id)
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

    fun updateCase(case: Case) {
        try {
            val data: MutableMap<String, Any> = HashMap();

            data[FIELD_NAME] = case.name
            data[FIELD_TYPE] = case.type
            data[FIELD_DESCRIPTION] = case.description
            data[FIELD_IMAGE] = case.image
            data[FIELD_REPORTER] = case.reporter
            data[FIELD_ISCLAIMED] = case.isClaimed
            data[FIELD_ID] = case.id
            data[FIELD_ADDRESS] = case.address
            data[FIELD_LAT] = case.lat
            data[FIELD_LNG] = case.lng
            data[FIELD_CONTACT_NUMBER] = case.contactNumber

            //for adding document to nested collection
            db.collection(COLLECTION_CASES)
                .document(case.id)
                .update(data)
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "updateCase: Successfully added to Database $docRef")
                }
                .addOnFailureListener { ex ->
                    Log.e(TAG, "updateCase: Exception ocurred while adding a document : $ex", )
                }


        } catch (ex: Exception) {
            Log.e(TAG, "updateCase: Couldn't perform insert on Expenses collection due to exception $ex", )
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
                            var cContactNumber = docChanges.document.data.get("${FIELD_CONTACT_NUMBER}")
                            var cLat = docChanges.document.data.get("${FIELD_LAT}")
                            var cLng = docChanges.document.data.get("${FIELD_LNG}")
                            var cName = docChanges.document.data.get("${FIELD_NAME}")


                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                "${cLat}".toDouble(),
                                "${cLng}".toDouble(),
                                "${cisClaimed}".toBoolean(),
                                "${cID}",
                                )
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

    fun retrieveCasesByEmail(email: String) {
        try {
            db
                .collection(COLLECTION_CASES)
                .addSnapshotListener(EventListener { result, error ->
                    if (error != null) {
                        Log.e(TAG, "retrieveCasesByEmail: Listening to Expenses collection Failed due to error : $error")
                        return@EventListener
                    }

                    if (result != null) {
                        Log.d(TAG, "retrieveCasesByEmail: Number of Documents retrieved : ${result.size()}")

                        val tempList: MutableList<Case> = ArrayList<Case>()

                        for (docChanges in result.documentChanges) {

                            var cType = docChanges.document.data.get("$FIELD_TYPE")
                            var cDescription = docChanges.document.data.get("$FIELD_DESCRIPTION")
                            var cImage = docChanges.document.data.get("$FIELD_IMAGE")
                            var cReporter = docChanges.document.data.get("$FIELD_REPORTER")
                            var cisClaimed = docChanges.document.data.get("$FIELD_ISCLAIMED")
                            var cID = docChanges.document.data.get("$FIELD_ID")
                            var cAddress = docChanges.document.data.get("$FIELD_ADDRESS")
                            var cContactNumber = docChanges.document.data.get("${FIELD_CONTACT_NUMBER}")
                            var cLat = docChanges.document.data.get("${FIELD_LAT}")
                            var cLng = docChanges.document.data.get("${FIELD_LNG}")
                            var cName = docChanges.document.data.get("${FIELD_NAME}")


                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                "${cLat}".toDouble(),
                                "${cLng}".toDouble(),
                                "${cisClaimed}".toBoolean(),
                                "${cID}",
                            )
                            Log.d(TAG, "retrieveCasesByEmail: current Document : ${case}")

                            when (docChanges.type) {
                                DocumentChange.Type.ADDED -> {
                                    if(email == case.reporter) {
                                        tempList.add(case)
                                    }
                                }
                                DocumentChange.Type.MODIFIED -> {}
                                DocumentChange.Type.REMOVED -> {}
                            }
                        }
                        Log.d(TAG, "retrieveCasesByEmail: before tempList : $tempList")
                        //replace the value in allExpenses
                        allCases.postValue(tempList)
                        Log.d(TAG, "retrieveCasesByEmail: after add tempList : $tempList")
                    } else {
                        Log.d(TAG, "retrieveCasesByEmail: No data in the result after retrieving")
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
                            var cContactNumber = docChanges.document.data.get("${FIELD_CONTACT_NUMBER}")
                            var cLat = docChanges.document.data.get("${FIELD_LAT}")
                            var cLng = docChanges.document.data.get("${FIELD_LNG}")
                            var cName = docChanges.document.data.get("${FIELD_NAME}")


                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                "${cLat}".toDouble(),
                                "${cLng}".toDouble(),
                                "${cisClaimed}".toBoolean(),
                                "${cID}",
                            )
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
                            var cContactNumber = docChanges.document.data.get("${FIELD_CONTACT_NUMBER}")
                            var cLat = docChanges.document.data.get("${FIELD_LAT}")
                            var cLng = docChanges.document.data.get("${FIELD_LNG}")
                            var cName = docChanges.document.data.get("${FIELD_NAME}")


                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                "${cLat}".toDouble(),
                                "${cLng}".toDouble(),
                                "${cisClaimed}".toBoolean(),
                                "${cID}",
                            )
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

    fun deleteCase(caseId: String){
        try{
            db.collection(COLLECTION_CASES)
                .document(caseId)
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