package com.example.tracker.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class LocationViewModel: ViewModel() {

     private val _location = MutableLiveData<MutableMap<String, GeoPoint>>()

        fun getLocationData(): MutableLiveData<MutableMap<String, GeoPoint>> {

            return _location
        }

    val db = FirebaseFirestore.getInstance()


    /**
     * Get the location of my partner
     */

    fun getLocation(){
        val docRef = db.collection("locations").document("daniel")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAGIN", "DocumentSnapshot data: ${document.data}")

                } else {
                    Log.d("NODOCUMENT", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FAILURE", "get failed with ", exception)
            }
    }

    /**
     * Give realtime updates on my location
     */
    fun updateLocation(location: GeoPoint){
        val docRef = db.collection("locations").document("samuel")
        var map = mutableMapOf<String, Any>()

        map["location"] = location
        docRef
            .update(map)
            .addOnSuccessListener {
                Log.d("SUCCESS", "SUCCESSFUL")
            }.addOnFailureListener{
                Log.d("FAILURE", "$it")

            }
    }


    /**
     * Give realtime updates on the location of my partner
     */
    fun checkForUpdates(){
        val docRef = db.collection("locations").document("daniel")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("LISTFAILED", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("SOMETHING", "Current data: ${snapshot.data}")


                 _location.value  = snapshot.data as MutableMap<String, GeoPoint>?


            } else {
                Log.d("NOTHING", "Current data: null")
            }
        }
    }

}