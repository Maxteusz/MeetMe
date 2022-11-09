package com.example.meetme.Controllers

import android.annotation.SuppressLint
import android.util.Log
import com.example.meetme.Fragments.RequestFragment
import com.example.meetme.Models.Request
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RequestFragmentController(val requestFragment: RequestFragment) {

    private val db = Firebase.firestore


    @SuppressLint("SuspiciousIndentation")
    fun getRequests(): MutableList<Request> {
        var requests: MutableList<Request> = ArrayList()

        db.collection("Requests")
            .whereEqualTo("ownerInvitation", StartUpController.currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val request = document.toObject<Request>()
                    if(!request.accepted)
                    requests.add(request)
                }
                Log.i("Pobrano ż ządania ${requests.count()}", "fd")
            }





        return requests


    }


}