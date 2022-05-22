package com.example.meetme.Controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Models.Invited
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MyInvitationsFragmentController {
    val myInvitationsFragment : MyInvitedFragment


    private val db = Firebase.firestore
    val invitations : MutableList<Invited>;


    constructor(myInvitationsFragment: MyInvitedFragment) {
        this.myInvitationsFragment = myInvitationsFragment
        invitations = getMyInvitations()

    }



    private fun getMyInvitations() : MutableList<Invited>
    {
        var invitations : MutableList<Invited> = ArrayList()
              db.collection("Invitations")
            .whereEqualTo(FieldPath.of("owner", "uid"),StartUpController.loggedUser.uid)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                invitations.clear()
                for (doc in value!!) {
                    val invited = doc.toObject<Invited>()
                    invited.uid = doc.id
                        invitations.add(invited)
                    Log.i("test", invitations[0].uid.toString())
                    }

                myInvitationsFragment.adapter?.notifyDataSetChanged()

            }
        return invitations
    }
}


