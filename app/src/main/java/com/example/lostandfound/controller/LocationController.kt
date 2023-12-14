package com.example.lostandfound.controller

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.Locale

class LocationController(private val activity: FragmentActivity) {
    private val TAG: String = "LocationController"
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    private val APP_PERMISSIONS_LIST = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val multiplePermissionsResultLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultsList ->
            Log.d(TAG, resultsList.toString())
            val allPermissionsGrantedTracker = resultsList.all { it.value }
            if (allPermissionsGrantedTracker) {
                getDeviceLocation {
                    location -> Log.d(TAG, "${location}")
                }
            } else {
                handlePermissionDenied()
            }
        }

    fun launchPermissions() {
        multiplePermissionsResultLauncher.launch(APP_PERMISSIONS_LIST)
    }

    fun getDeviceLocation(callback: (Location) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            multiplePermissionsResultLauncher.launch(APP_PERMISSIONS_LIST)
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    Log.d(TAG, "Location is null")
                    return@addOnSuccessListener
                }
                Log.d(TAG, "location: ${location}")
                callback(location)
            }
    }

     fun getAddress(location: Location, callback: (Address) -> Unit) {
        val geocoder: Geocoder = Geocoder(activity, Locale.getDefault())
        try {
            val searchResults: List<Address>? =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (searchResults == null || searchResults.isEmpty()) {
                Log.e(TAG, "Getting Street Address: SearchResults is NULL or empty")
            } else {
                callback(searchResults[0])
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error encountered while getting coordinate location.")
            Log.e(TAG, ex.toString())
        }
    }

    fun getLocation(address: String, callback: (Address) -> Unit) {
        val geocoder: Geocoder = Geocoder(activity, Locale.getDefault())
        try {
            val searchResults:MutableList<Address>? = geocoder.getFromLocationName(address, 1)
            if (searchResults == null) {
                Log.e(TAG, "searchResults variable is null")
                return
            }

            if (searchResults.size == 0) {
            } else {
                val foundLocation: Address = searchResults.get(0)
                callback(foundLocation)

            }
        } catch(ex:Exception) {
            Log.e(TAG, "Error encountered while getting coordinate location.")
            Log.e(TAG, ex.toString())
        }
    }

    private fun handlePermissionDenied() {
        // Handle permission denied scenario, e.g., show a message to the user
    }
}
