package com.example.tracker.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.example.tracker.utils.LocationViewModel
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.GeoPoint
import java.util.concurrent.TimeUnit


class LocationServices: Service() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    //private val localBinder = LocalBinder()


    /**
     * Starts the services
     */
    override fun onCreate() {
        super.onCreate()

        Log.d("SERVICES", "Services created")

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest().apply {

            interval = TimeUnit.SECONDS.toMillis(1)


            Log.d("SERVICES", "location request showing")

            fastestInterval = TimeUnit.SECONDS.toMillis(1)

            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        /**
         * Check for the callback
         */
        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                if (locationResult?.lastLocation != null) {


                 var locationViewModel = LocationViewModel()

                    var latitude = locationResult?.lastLocation.latitude
                    var longitude = locationResult?.lastLocation.longitude

                    locationViewModel.updateLocation(GeoPoint(latitude, longitude ))
                     Log.d("LOCATIONSA", "$latitude")




                    }
                else {
                    Log.d("SERVICESS", "Location information isn't available.")
                }
            }

        }


        startCheckingLocation()
    }


    override fun onBind(p0: Intent?): IBinder? {



      return null
    }

    /**
     * Checks for the locayion and gives instant updated
     */

    fun startCheckingLocation(){
        Log.d("SERVICES", "subscribeToLocationUpdates()")


        startService(Intent(applicationContext, LocationServices::class.java))

        try {
            //   Subscribe to location changes.
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

            Log.d("SERVICES", "STARTED UPDATES")


        } catch (unlikely: SecurityException) {

            Log.e("SERVICES", "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }
}