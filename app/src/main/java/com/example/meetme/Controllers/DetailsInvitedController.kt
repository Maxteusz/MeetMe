package com.example.meetme.Controllers

import android.content.ContentValues
import android.util.Log
import com.example.meetme.Activities.DetailsInviteActivity
import com.example.meetme.Controllers.StartUpController.Companion.db
import com.example.meetme.Models.Message
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class DetailsInvitedController(val detailsInviteActivity: DetailsInviteActivity) {

    fun getMessages(): MutableList<Message> {
        var messages: MutableList<Message> = ArrayList()
        db.collection("Messages")
            .orderBy("date", Query.Direction.ASCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                messages.clear()
                for (doc in value!!) {
                    val message = doc.toObject<Message>()
                    messages.add(message)

                }
                detailsInviteActivity.adapter?.notifyDataSetChanged()
            }
        return messages
    }
}