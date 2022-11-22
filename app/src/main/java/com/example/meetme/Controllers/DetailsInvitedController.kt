package com.example.meetme.Controllers

import android.util.Log
import com.example.meetme.Activities.DetailsInviteActivity
import com.example.meetme.Models.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class DetailsInvitedController(val detailsInviteActivity: DetailsInviteActivity) {

    fun getMessages(): MutableList<Message> {
        var messages: MutableList<Message> = ArrayList()
        val db = Firebase.firestore
        db.collection("Messages")
            .addSnapshotListener { result, error ->
                if (result != null) {

                    messages = result.toObjects<Message>() as MutableList<Message>

                    detailsInviteActivity.adapter!!.notifyDataSetChanged()
                    Log.i("Messages", messages.size.toString())
                }
                detailsInviteActivity.adapter!!.notifyDataSetChanged()
            }
        return messages
    }
}