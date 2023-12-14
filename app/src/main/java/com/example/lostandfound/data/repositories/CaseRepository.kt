package com.example.lostandfound.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lostandfound.models.Case
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
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
    private val FIELD_NAME = "name"
    private val FIELD_GEO_POINT = "geoPoint"
    private val FIELD_CONTACT_NUMBER = "contactNumber"

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
            data[FIELD_GEO_POINT] = com.google.firebase.firestore.GeoPoint(
                newCase.geoPoint.latitude,
                newCase.geoPoint.longitude
            )
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
            data[FIELD_GEO_POINT] = case.geoPoint
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
                            var cName = docChanges.document.data.get("${FIELD_NAME}")
                            var cGeoPoint = docChanges.document.getGeoPoint(FIELD_GEO_POINT)
                            var geoPoint = com.google.firebase.firestore.GeoPoint(
                                0.0,
                                0.0
                            )

                            if(cGeoPoint != null) {
                                geoPoint = com.google.firebase.firestore.GeoPoint(
                                    cGeoPoint.latitude,
                                    cGeoPoint.longitude
                                )
                            }

                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                geoPoint,
                                "${cisClaimed}".toBoolean(),
                                "${cID}",
                                )
                            Log.d(TAG, "retrieveAllCases: current Document : ${case}")

                            when (docChanges.type) {
                                DocumentChange.Type.ADDED -> {
                                    tempList.add(case)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    tempList
                                }
                                DocumentChange.Type.REMOVED -> {
                                    tempList.remove(case)
                                }
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
                    casesListener(result, error, email)
                })

        } catch (ex: java.lang.Exception) {
            Log.e(TAG, "retrieveCasesByEmail: Unable to retrieve all expenses : $ex")
        }
    }

    private fun casesListener(
        result: QuerySnapshot?,
        error: FirebaseFirestoreException?,
        email: String?
    ) {
        if (error != null) {
            Log.e(TAG, "casesListener: Listening to Expenses cases Failed due to error : $error")
            return
        }

        if (result != null) {
            Log.d(TAG, "casesListener: Number of Documents retrieved : ${result.size()}")
            val tempList: MutableList<Case> = ArrayList<Case>()
            for (docChanges in result.documentChanges) {
                var cName = docChanges.document.data.get("${FIELD_NAME}")
                var cType = docChanges.document.data.get("$FIELD_TYPE")
                var cDescription = docChanges.document.data.get("$FIELD_DESCRIPTION")
                var cImage = docChanges.document.data.get("$FIELD_IMAGE")
                var cReporter = docChanges.document.data.get("$FIELD_REPORTER")
                var cisClaimed = docChanges.document.data.get("$FIELD_ISCLAIMED")
                var cID = docChanges.document.data.get("$FIELD_ID")
                var cAddress = docChanges.document.data.get("$FIELD_ADDRESS")
                var cContactNumber = docChanges.document.data.get("${FIELD_CONTACT_NUMBER}")

                var geoPoint = GeoPoint(
                    0.0,
                    0.0
                )


                if (docChanges.document.contains("geoPoint")) {
                    geoPoint = docChanges.document.getGeoPoint(FIELD_GEO_POINT)!!
                    geoPoint = GeoPoint(geoPoint.latitude, geoPoint.longitude)
                }

                val case = Case(
                    "$cName",
                    "$cType",
                    "$cDescription",
                    "$cImage",
                    "$cReporter",
                    "$cAddress",
                    "${cContactNumber}",
                    geoPoint,
                    "$cisClaimed".toBoolean(),
                    "${cID}",
                )
                Log.d(TAG, "casesListener: current Document : ${case}")

                when (docChanges.type) {
                    DocumentChange.Type.ADDED -> {
                        if (email != null) {
                            if(email == case.reporter) {
                                tempList.add(case)
                            }
                        } else {
                            tempList.add(case)
                        }
                    }
                    DocumentChange.Type.MODIFIED -> {}
                    DocumentChange.Type.REMOVED -> {}
                }
            }
            Log.d(TAG, "casesListener: before tempList : $tempList")
            allCases.postValue(tempList)
            Log.d(TAG, "casesListener: after add tempList : $tempList")
        } else {
            Log.d(TAG, "casesListener: No data in the result after retrieving")
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
                            var cName = docChanges.document.data.get("${FIELD_NAME}")
                            var cGeoPoint = docChanges.document.getGeoPoint("${FIELD_GEO_POINT}")
                            var geoPoint = GeoPoint(
                                0.0,
                                0.0
                            )

                            if(cGeoPoint != null) {
                                geoPoint = GeoPoint(
                                    cGeoPoint.latitude,
                                    cGeoPoint.longitude
                                )
                            }


                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                geoPoint,
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

    fun retrieveCasesbyName(inputName : String) {
        try {
            db
                .collection(COLLECTION_CASES)
                .whereEqualTo(FIELD_NAME, inputName)
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
                            var cName = docChanges.document.data.get("${FIELD_NAME}")
                            var cGeoPoint = docChanges.document.getGeoPoint("${FIELD_GEO_POINT}")
                            var geoPoint = GeoPoint(
                                0.0,
                                0.0
                            )

                            if(cGeoPoint != null) {
                                geoPoint = GeoPoint(
                                    cGeoPoint.latitude,
                                    cGeoPoint.longitude
                                )
                            }



                            val case = Case(
                                "${cName}",
                                "$cType",
                                "$cDescription",
                                "$cImage",
                                "$cReporter",
                                "$cAddress",
                                "${cContactNumber}",
                                geoPoint,
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