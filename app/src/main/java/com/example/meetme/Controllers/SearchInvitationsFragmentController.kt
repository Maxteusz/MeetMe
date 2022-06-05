package com.example.meetme.Controllers

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchInvitationsFragmentController {
    val searchInvitationsFragment: SearchInvitationsFragment
    private var fusedLocationClient: FusedLocationProviderClient


    constructor(searchInvitationsFragment: SearchInvitationsFragment) {
        this.searchInvitationsFragment = searchInvitationsFragment
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(searchInvitationsFragment.requireContext())
    }

    fun getCurrentLocation()  = run {
        if (ActivityCompat.checkSelfPermission(
                searchInvitationsFragment.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                searchInvitationsFragment.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                searchInvitationsFragment.context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
          111
            );

        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken()
        {
            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                return CancellationTokenSource().token
            }

            override fun isCancellationRequested(): Boolean {
                return false
            }
        })
    }


    fun searchInvitations() {
        getCurrentLocation().addOnSuccessListener {
            val center = GeoLocation(it.latitude, it.longitude)
            val radiusInM = (5 * 1000).toDouble()
            val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
            val db = Firebase.firestore
            val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()
            for (b in bounds) {
                val q: Query = db.collection("Invitations")
                    .orderBy("geohash")
                    .startAt(b.startHash)
                    .endAt(b.endHash)
                tasks.add(q.get())
            }
            Tasks.whenAllComplete(tasks)
                .addOnCompleteListener {
                    val matchingDocs: MutableList<DocumentSnapshot> = ArrayList()
                    matchingDocs.clear()
                    for (task in tasks) {
                        val snap = task.result
                        for (doc in snap.documents) {
                            val lat = doc.getDouble("latitude")!!
                            val lng = doc.getDouble("longitude")!!

                            // We have to filter out a few false positives due to GeoHash
                            // accuracy, but most will match
                            val docLocation = GeoLocation(lat, lng)
                            val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                            if (distanceInM <= radiusInM) {
                                matchingDocs.add(doc)
                            }
                        }
                    }
                    searchInvitationsFragment.search_button?.text = matchingDocs.size.toString()
                    Log.i("Nearest invitations", matchingDocs.size.toString())
                }
                .addOnFailureListener { showDialogBox("Błąd, spróbuj pornownie.") }
        }



    }

    private fun showDialogBox(message: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(searchInvitationsFragment.context).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "OK",

            { dialog, which ->
                    dialog.dismiss()

            })
        alertDialog.show()
    }


}