package com.example.meetme.Models

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class Request {

    val isAccepted  = false;
    var invitedID : String = "";
    var ownerID : String = "";

    constructor(invitedID: String, ownerID: String) {
        this.invitedID = invitedID
        this.ownerID = ownerID
    }
    constructor()


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