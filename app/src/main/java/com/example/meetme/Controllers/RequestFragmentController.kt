package com.example.meetme.Controllers

import android.annotation.SuppressLint
import android.util.Log
import com.example.meetme.Fragments.RequestFragment
import com.example.meetme.Models.Request
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RequestFragmentController(val requestFragment: RequestFragment) {

    private val db = Firebase.firestore


    @SuppressLint("SuspiciousIndentation")
    fun getRequests(): MutableList<Request> {
        var requests: MutableList<Request> = ArrayList()


        db.collection("Requests")
            .whereEqualTo(FieldPath.of("invited","owner","uid"),StartUpController.currentUser?.uid!!)
            .whereEqualTo("accepted", false)
            .addSnapshotListener { result, error ->
                if (result != null) {
                    requests.clear()
                    for (document in result) {
                        val request = document.toObject<Request>()
                        requests.add(request)
                        requestFragment.adapter!!.notifyDataSetChanged()
                    }
                }

            }
        return requests


    }


}