package com.example.meetme.Models

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Message : java.io.Serializable {

    var nickUser: String? = null
    var message: String? = ""
    var date: Date? = Calendar.getInstance().time

    constructor()

    constructor(nickUser : String, message: String?) {
        this.nickUser = nickUser
        this.message = message
        this.date = date
    }


    fun sendMessage(invited: Invited) {
        val db = Firebase.firestore
        db.collection("Invitations").document(invited.id.toString())
            .update("messages", FieldValue.arrayUnion(this))
            .addOnSuccessListener {}

    }
}