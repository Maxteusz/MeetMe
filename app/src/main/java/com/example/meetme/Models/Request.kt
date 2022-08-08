package com.example.meetme.Models

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Request(val invitedID: String, val ownerID: String) {

companion object {

    fun sendRequest( personID : String, invitedID: String, ownerID: String, successFunction: () -> Unit, failFunction: () -> Unit)
    {
        val db = Firebase.firestore
        db.collection("Requests")
            .add(Request(invitedID, ownerID))
            .addOnSuccessListener {
                successFunction()
            }
            .addOnFailureListener {
                failFunction()
            }
    }

}


}