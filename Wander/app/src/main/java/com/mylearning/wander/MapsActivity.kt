package com.mylearning.wander

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.lifecycle.Observer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.GeoPoint
import com.mylearning.wander.util.MapLocationViewModel
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Application name : Locate Your partner
 *
 */
private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var map: GoogleMap

    private val TAG = MapsActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // View model provider
        val viewModel = ViewModelProvider(this).get(MapLocationViewModel::class.java)
        viewModel.confirmUpdates()

        viewModel.updateLocation(GeoPoint(34.5, 57.6))

        // View model observe method to observe current location
        viewModel.getLocationData().observe(this, Observer {
            Log.d("Observer", "${it["location"]}")
            val s = it["location"]
            val lat =  s?.latitude
            Log.d("Observer", "$lat")
            val long = s?.longitude
            val currentLatLng = LatLng(lat!!, long!!)

            map.clear()

            // zoom level
            val zoomLevel = 15f
            // move the camera to my location
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomLevel))

            // Adding image to maker
            var bitmapImage : Bitmap? = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.samuel), 90, 150, true)
            // Add marker styling
            map.addMarker(MarkerOptions().position(currentLatLng).title("Samuel Location")
                .icon(BitmapDescriptorFactory.fromBitmap(bitmapImage)))



            // call user add marker func
            setMapLongClick(map)



        })

        // if Foreground Permission is granted start location
        if (foregroundPermissionApproved()){
            startLocation()
        } else{
            // request fore ground permission
            requestForegroundPermissions()
        }

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    // this places a marker by default on sydney
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // latitude of my location
        val latitude = 6.474364
        // longitude of my location
        val longitude = 3.631117

        //LatLng object
        val homeLatLng = LatLng(latitude, longitude)

        // zoom level
        val zoomLevel = 15f
        // move the camera to my location
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))

        // Add marker to the map at homeLatLng
        map.addMarker(MarkerOptions().position(homeLatLng))

        // call user add marker func
        setMapLongClick(map)

        // style maker
        setMapStyle(map)


    }

    // menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Changing the map type based on user selection
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true

        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true

        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true

        }
        else -> super.onOptionsItemSelected(item)
    }

    // Function that allows users to add markers
    private fun setMapLongClick(map : GoogleMap) {
       // Toast.makeText(this, "Adding markers", Toast.LENGTH_SHORT).show()

        map.setOnMapClickListener {LatLng ->
            // Adding snippet
            val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    LatLng.latitude,
                    LatLng.longitude
            )
            map.addMarker(MarkerOptions()
                    .position(LatLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet))
        }
    }

    // Point of interest listener
    private fun setPoiClick(map : GoogleMap) {
        map.setOnMapClickListener { poi ->
            val poiMarker = map.addMarker(
                    MarkerOptions()
                            // error!!!

            )
        }
    }

    // Network perssion
    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
    private fun requestForegroundPermissions() {
        val provideRationale = foregroundPermissionApproved()
        // If the user denied a previous request, but didn't check "Don't ask again", provide
        // additional rationale.
        if (provideRationale) {
            // Request permission
            ActivityCompat.requestPermissions(
                this@MapsActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )

        } else {
            Log.d("PERMSISSION", "Request foreground only permission")
            ActivityCompat.requestPermissions(
                this@MapsActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d("PERMISSION", "onRequestPermissionResult")
        when (requestCode) {
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE -> when {
                grantResults.isEmpty() ->
                    // If user interaction was interrupted, the permission request
                    // is cancelled and you receive empty arrays.
                    Log.d("PERMISSION", "User interaction was cancelled.")
                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    // Permission was granted.
                        startLocation()
                else -> {
                    // Permission denied.
                    // Build intent that displays the App settings screen.
                    val intent = Intent()
                    val uri = Uri.fromParts(
                        "package",
                        BuildConfig.APPLICATION_ID,
                        null
                    )
                    intent.data = uri
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
    }

    private fun startLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(1)

            Log.d("SERVICES", "location request showing")
            fastestInterval = TimeUnit.SECONDS.toMillis(1)
            maxWaitTime = TimeUnit.MINUTES.toMillis(1)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (locationResult?.lastLocation != null) {

                    val locationViewModel = MapLocationViewModel()
                    val latitude = locationResult?.lastLocation.latitude
                    val longitude = locationResult?.lastLocation.longitude
                    locationViewModel.updateLocation(GeoPoint(latitude, longitude ))
                    Log.d("LOCATIONSA", "$latitude")


                }
                else {
                    Log.d("SERVICESS", "Location information isn't available.")
                }
            }
        }

        try {

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            Log.d("SERVICES", "STARTED UPDATES")

        } catch (unlikely: SecurityException) {
            Log.e("SERVICES", "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }
}