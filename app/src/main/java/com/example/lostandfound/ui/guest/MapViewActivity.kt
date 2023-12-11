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
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import java.util.Locale

class MapViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapViewBinding
    private val TAG = "MapView"

    private lateinit var mapView : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: $caseArrayList")


        var out = ""
        for (i in caseArrayList) {
            out += "\n${i.description}"
            getCoordinatesandaddAnnotation(i.address)
        }
        binding.tvResults.setText("MapView Results for : $out")
    }

    private fun getCoordinatesandaddAnnotation(address : String) {
        //NOTE: with this line, it will clean up all the PINNED places before it pins the new one
//        binding.mapView.annotations.cleanup()

        // forward geocoding: (street address --> latitude/longitude)


        // 1. Create an instance of the built in Geocoder class
        val geocoder: Geocoder = Geocoder(applicationContext, Locale.getDefault())


        // - get the address from the user interface
        val addressFromUI = address


        Log.d(TAG, "Getting coordinates for ${addressFromUI}")


        // 2. try to get the coordinate
        try {
            val searchResults:MutableList<Address>? = geocoder.getFromLocationName(addressFromUI, 5)
            if (searchResults == null) {
                // Log.e --> outputs the message as an ERROR (red)
                // Log.d --> outputs the message as a DEBUG message
                Log.e(TAG, "searchResults variable is null")
            }


            // if not null, then we were able to get some results (and it is possible for the results to be empty)
            if (searchResults!!.size == 0) {
                binding.tvResults.setText("Search results are empty.")
            } else {
                // 3. Get the coordinate
                val foundLocation: Address = searchResults.get(0)
                // 4. output to screen
//                var message = "Coordinates are: ${foundLocation.latitude}, ${foundLocation.longitude}"
//                binding.tvResults.setText(message)
//                Log.d(TAG, message)

                //FUNCTION: Pinpoint the coordinates in the map
                addAnnotationToMap(foundLocation.latitude, foundLocation.longitude)
            }

        } catch(ex:Exception) {
            Log.e(TAG, "Error encountered while getting coordinate location.")
            Log.e(TAG, ex.toString())
        }
    }

    private fun addAnnotationToMap(lat:Double, lng:Double, @DrawableRes drawableImageResourceId: Int = R.drawable.red_marker) {
        Log.d(TAG, "Attempting to add annotation to map")

        // get the image you want to use as a map marker
        // & resize it to fit the proportion of the map as the user zooms in and zoom out
        // Or you can declare: val icon = MapboxUtils.bitmapFromDrawableRes(applicationContext, R.drawable.pikachu)
        val icon = MapboxUtils.bitmapFromDrawableRes(applicationContext, drawableImageResourceId)
        //NOTE: need to create MapboxUtils object class


        // error handling code: sometimes, the person may provide an image that cannot be
        // properly converted to a map marker
        if (icon == null) {
            Log.d(TAG, "ERROR: Unable to convert provided image into the correct format.")
            return
        }


        // code sets up the map so you can add markers
        val annotationApi = binding.mapView?.annotations
        val pointAnnotationManager =
            annotationApi?.createPointAnnotationManager(
                AnnotationConfig(
                    annotationSourceOptions = AnnotationSourceOptions(maxZoom = 80)
                )
            )

        // Create a marker & configure the options for that marker
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(lng, lat))
            .withIconImage(icon)

        // Add the resulting pointAnnotation to the map.
        pointAnnotationManager?.create(pointAnnotationOptions)


    }


}