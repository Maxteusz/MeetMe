package com.example.meetme.Controllers

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
    val searchInvitationsFragment : SearchInvitationsFragment
   private var database = FirebaseDatabase.getInstance("https://meetme-5a1e5-default-rtdb.firebaseio.com").getReference("Invitations")

    constructor(searchInvitationsFragment: SearchInvitationsFragment) {
        this.searchInvitationsFragment = searchInvitationsFragment
    }

     fun searchInvitations()
    {
        //test
        val center = GeoLocation(51.5074, 0.1278)
        val radiusInM = (50 * 1000).toDouble()
        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
        val db = Firebase.firestore
        val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()
        for (b in bounds) {
            val q: Query = db.collection("Invitations")
                .orderBy("location")
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
                        val lat = doc.getDouble("latitute")!!
                        val lng = doc.getDouble("longtitude")!!

                        // We have to filter out a few false positives due to GeoHash
                        // accuracy, but most will match
                        val docLocation = GeoLocation(lat, lng)
                        val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                        if (distanceInM <= radiusInM) {
                            matchingDocs.add(doc)
                        }
                        Log.i("Found", matchingDocs.size.toString())
                    }
                }

                Log.i("Found", matchingDocs.size.toString())
                // ...
            }
    }


}