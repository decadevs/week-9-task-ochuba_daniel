package com.mylearning.wander.util

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

// Map location view model extending View model method
class MapLocationViewModel : ViewModel() {

    private val _location = MutableLiveData<MutableMap<String, GeoPoint>>()
    fun getLocationData(): MutableLiveData<MutableMap<String, GeoPoint>> {
        return _location
    }

    // Fire base instance
    val db = FirebaseFirestore.getInstance()



    fun getLocation() {
        val docRef = db.collection("locations").document("samuel")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("DataSnapshot", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("NoDoc", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Failure", "get failed with ", exception)
            }
    }

    fun updateLocation(geoPoint: GeoPoint) {
        val docRef = db.collection("locations").document("daniel")
        var map = mutableMapOf<String, Any>()
        map["location"] = geoPoint
        docRef
            .update(map)
            .addOnSuccessListener {
                Log.d("SUCCESS", "SUCCESSFUL")
            }.addOnFailureListener{
                Log.d("FAILURE", "$it")
            }
    }

    // Check and comfirm update from the database
    fun confirmUpdates () {
        val docRef = db.collection("locations").document("samuel")
       // Snap shot of partner location
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("isFailed", "Listen failed.", e)
                return@addSnapshotListener
            }
            // If snapshot is not empty, using live Data to get the current position
            if (snapshot != null && snapshot.exists()) {
                Log.d("CurrentData", "Current data: ${snapshot.data}")

                _location.value = snapshot.data as MutableMap<String, GeoPoint>?

            } else {
                Log.d("DataNull", "Current data: null")
            }
        }
    }
}