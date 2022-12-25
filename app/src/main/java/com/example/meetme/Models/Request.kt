package com.example.meetme.Models

import android.content.ContentValues
import android.util.Log
import com.example.meetme.Controllers.StartUpController
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class Request : Serializable {

    var id: String? = null;
    val accepted: Boolean = false;
    var ownerRequest: User? = null;
    var invited: Invited? = null

    constructor()

    constructor(ownerRequest: User, invited: Invited) {

        this.ownerRequest = ownerRequest
        this.invited = invited
    }

    constructor(id: String, ownerRequest: User, invited: Invited) {
        this.id = id
        this.ownerRequest = ownerRequest
        this.invited = invited
    }


    companion object {
        fun deleteRequest(request: Request) {
            val db = Firebase.firestore
            db.collection("Requests").document(request.id.toString())
                .delete()
                .addOnSuccessListener {

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

    fun sendRequest(
        successFunction: () -> Unit,
        failFunction: () -> Unit
    ) {
        val db = Firebase.firestore
        id =  db.collection("Requests").document().id;

        db.collection("Requests").document(id!!)
            .set(this)
            .addOnSuccessListener {
                successFunction()
            }
            .addOnFailureListener {
                failFunction()
            }
    }


}