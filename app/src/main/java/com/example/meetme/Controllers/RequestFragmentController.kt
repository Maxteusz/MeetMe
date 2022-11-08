package com.example.meetme.Controllers

import com.example.meetme.Fragments.RequestFragment
import com.example.meetme.Models.Request
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RequestFragmentController(val requestFragmentController: RequestFragment) {

    private val db = Firebase.firestore


    fun getRequests () : MutableList<Request>
    {
        var request : MutableList<Request> = ArrayList<Request>()
        /*db.collection("Requests")
            .whereEqualTo(FieldPath.of("owner", "uid"),StartUpController.currentUser?.uid!!)*/
        return request





    }


}