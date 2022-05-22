package com.example.meetme.Controllers

import android.location.Geocoder
import android.util.Log
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchInvitationsFragmentController {
    val searchInvitationsFragment: SearchInvitationsFragment


    constructor(searchInvitationsFragment: SearchInvitationsFragment) {
        this.searchInvitationsFragment = searchInvitationsFragment
    }

    fun searchInvitations() {
        val center = GeoLocation(50.4467685, 18.8202764)
        val radiusInM = (50 * 1000).toDouble()
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
                Log.i("Nearest invitations", matchingDocs.size.toString())

                // matchingDocs contains the results
                // ...
            }


    }


}