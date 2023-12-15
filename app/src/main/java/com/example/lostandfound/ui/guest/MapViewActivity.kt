package com.example.lostandfound.ui.guest

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import com.example.lostandfound.R
import com.example.lostandfound.controller.MapboxUtils
import com.example.lostandfound.databinding.ActivityMapViewBinding
import com.google.type.LatLng
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.util.Locale

class MapViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapViewBinding
    private val TAG = "MapView"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: $caseArrayList")


        var out = ""
        for (i in caseArrayList) {
            out += "\n${i.name}"
            if(caseArrayList.size > 1) {
                getCoordinatesandaddAnnotation(i.address, i.name)
                } else {
                    getCoordinatesandaddAnnotationforOne(i.address, i.name)
                }
            }

        binding.tvResults.setText("MapView Results for : $out")

    }

    private fun getCoordinatesandaddAnnotation(address : String, name: String) {
        val geocoder: Geocoder = Geocoder(applicationContext, Locale.getDefault())

        val addressFromUI = address


        Log.d(TAG, "Getting coordinates for ${addressFromUI}")

        try {
            val searchResults:MutableList<Address>? = geocoder.getFromLocationName(addressFromUI, 5)
            if (searchResults == null) {
                Log.e(TAG, "searchResults variable is null")
            }
            if (searchResults!!.size == 0) {
                binding.tvResults.setText("Search results are empty.")
            } else {
                val foundLocation: Address = searchResults.get(0)
                addAnnotationToMap(foundLocation.latitude, foundLocation.longitude, name)
            }

        } catch(ex:Exception) {
            Log.e(TAG, "Error encountered while getting coordinate location.")
            Log.e(TAG, ex.toString())
        }
    }

    private fun addAnnotationToMap(lat:Double, lng:Double, name :String, @DrawableRes drawableImageResourceId: Int = R.drawable.red_marker) {
        Log.d(TAG, "Attempting to add annotation to map")

        val icon = MapboxUtils.bitmapFromDrawableRes(applicationContext, drawableImageResourceId)
        if (icon == null) {
            Log.d(TAG, "ERROR: Unable to convert provided image into the correct format.")
            return
        }

        val annotationApi = binding.mapView?.annotations
        val pointAnnotationManager =
            annotationApi?.createPointAnnotationManager(
                AnnotationConfig(
                    annotationSourceOptions = AnnotationSourceOptions(maxZoom = 80)
                )
            )

        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(lng, lat))
            .withIconImage(icon)
            .withTextField("${name}")  // Set your label text here
            .withTextOffset(arrayOf(0.0, 2.0).toList())   // Adjust the offset as needed
            .withTextSize(12.0)                  // Set the text size as needed
//            .withTextAnchor()              // Anchor the text to the top of the icon
//            .withTextJustify("auto")

        pointAnnotationManager?.create(pointAnnotationOptions)
    }

    private fun getCoordinatesandaddAnnotationforOne(address : String, name: String) {
        val geocoder: Geocoder = Geocoder(applicationContext, Locale.getDefault())

        val addressFromUI = address


        Log.d(TAG, "Getting coordinates for ${addressFromUI}")

        try {
            val searchResults:MutableList<Address>? = geocoder.getFromLocationName(addressFromUI, 5)
            if (searchResults == null) {
                Log.e(TAG, "searchResults variable is null")
            }
            if (searchResults!!.size == 0) {
                binding.tvResults.setText("Search results are empty.")
            } else {
                val foundLocation: Address = searchResults.get(0)
                addAnnotationToMap(foundLocation.latitude, foundLocation.longitude, name)

                //camera
                binding.mapView.mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(foundLocation.longitude, foundLocation.latitude))
                        .pitch(45.0)
                        .zoom(12.5)
                        .bearing(-17.6)
                        .build()
                )
            }

        } catch(ex:Exception) {
            Log.e(TAG, "Error encountered while getting coordinate location.")
            Log.e(TAG, ex.toString())
        }
    }
}