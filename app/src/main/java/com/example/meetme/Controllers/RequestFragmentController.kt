package com.example.meetme.Controllers

import android.annotation.SuppressLint
import android.util.Log
import com.example.meetme.Fragments.RequestFragment
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Request
import com.example.meetme.Models.User
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RequestFragmentController(val requestFragment: RequestFragment) {

    private val db = Firebase.firestore


    @SuppressLint("SuspiciousIndentation")
    fun getRequests(): MutableList<Request> {
        var requests: MutableList<Request> = ArrayList()


        db.collection("Requests")
            .whereEqualTo(FieldPath.of("invited","owner","uid"),StartUpController.currentUser?.uid!!)
            .whereEqualTo("accepted", false)
            .addSnapshotListener { result, error ->
                requests.clear()
                if (result != null) {

                    for (document in result) {
                        val request = document.toObject<Request>()
                        requests.add(request)

                    }
                }
                requestFragment.adapter!!.notifyDataSetChanged()
            }
        return requests


    }

    fun addMember (request: Request, invited : Invited, member : User)
    {
        val db = Firebase.firestore
        db.collection("Invitations").document(invited.id.toString())
            .update("members", FieldValue.arrayUnion(member))
            .addOnSuccessListener {
                Request.deleteRequest(request)
            }


    }


}