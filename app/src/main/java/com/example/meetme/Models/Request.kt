package com.example.meetme.Models

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class Request : Serializable {

    @DocumentId
    var id: String? = null;
    val accepted : Boolean  = true;
    var invitedID: String = "";
    var ownerID: String = "";
    var ownerInvitation : String = ""

    constructor()

    constructor(invitedID: String, ownerID: String, ownerInvitation: String) {
        this.invitedID= invitedID
        this.ownerID = ownerID
        this.ownerInvitation = ownerInvitation
    }




    companion object {

        fun sendRequest(
            personID: String,
            invitedID: String,
            ownerID: String,
            ownerInvitation: String,
            successFunction: () -> Unit,
            failFunction: () -> Unit
        ) {
            val db = Firebase.firestore
            db.collection("Requests")
                .add(Request(invitedID, ownerID, ownerInvitation))
                .addOnSuccessListener {
                    successFunction()
                }
                .addOnFailureListener {
                    failFunction()
                }
        }

        fun deleteRequest(uid: String) {
            val db = Firebase.firestore
            db.collection("Requests").document(uid)
                .delete()
                .addOnSuccessListener {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot successfully deleted!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(
                        ContentValues.TAG,
                        "Error deleting document",
                        e
                    )
                }
        }


    }


}