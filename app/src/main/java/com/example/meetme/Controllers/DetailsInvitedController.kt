package com.example.meetme.Controllers

import android.content.ContentValues
import android.util.Log
import com.example.meetme.Activities.DetailsInviteActivity
import com.example.meetme.Controllers.StartUpController.Companion.db
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Message
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class DetailsInvitedController(val detailsInviteActivity: DetailsInviteActivity) {

    fun getMessages(invited : Invited): MutableList<Message> {
        var messages: MutableList<Message> = ArrayList()
      db.collection("Invitations").document(invited.id.toString())
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                messages.clear()
                val temp = value?.toObject<Invited>()
                Log.i("ddd", temp?.messages?.size.toString())
                if(temp?.messages != null) {
                    messages = (temp!!.messages as MutableList<Message>?)!!
                    detailsInviteActivity.adapter?.notifyDataSetChanged()
                }

                }
                detailsInviteActivity.adapter?.notifyDataSetChanged()

        return messages
    }
}