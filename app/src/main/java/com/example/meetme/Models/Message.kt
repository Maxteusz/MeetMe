package com.example.meetme.Models

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class Message : java.io.Serializable {

    var user: User? = null
    var message: String? = ""
    var idInvited: Int = -1
    var date: Date? = Calendar.getInstance().time

    constructor()

    constructor(user: User?, message: String?, idInvited: Int) {
        this.user = user
        this.message = message
        this.idInvited = idInvited
        this.date = date
    }


    fun sendMessage() {
        val db = Firebase.firestore
        db.collection("Messages")
            .add(this)
            .addOnSuccessListener {}

    }
}