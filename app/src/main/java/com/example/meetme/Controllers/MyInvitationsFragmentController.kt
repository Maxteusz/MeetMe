package com.example.meetme.Controllers

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.example.meetme.Fragments.MyInvitationsFragment
import com.example.meetme.Models.Invited
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MyInvitationsFragmentController {
    val myInvitationsFragment : MyInvitationsFragment


    private val db = Firebase.firestore



    constructor(myInvitationsFragment: MyInvitationsFragment) {
        this.myInvitationsFragment = myInvitationsFragment


    }

    @SuppressLint("SuspiciousIndentation")
    public fun getMyInvitations() : MutableList<Invited>
    {
        var invitations : MutableList<Invited> = ArrayList()
              db.collection("Invitations")
            .whereEqualTo(FieldPath.of("owner", "uid"),StartUpController.currentUser?.uid!!)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                invitations.clear()
                for (doc in value!!) {
                    val invited = doc.toObject<Invited>()
                        invitations.add(invited)

                }

                myInvitationsFragment.adapter?.notifyDataSetChanged()

            }
        return invitations
    }

    public fun getInvitationsWhichIamAsGuest() : MutableList<Invited>
    {
        var invitations : MutableList<Invited> = ArrayList()
        db.collection("Invitations")
            .whereEqualTo(FieldPath.of("owner", "uid"),StartUpController.currentUser?.uid!!)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                invitations.clear()
                for (doc in value!!) {
                    val invited = doc.toObject<Invited>()
                    invitations.add(invited)

                }

                myInvitationsFragment.adapter?.notifyDataSetChanged()

            }
        return invitations
    }

    fun deleteInvitation (uid : String)
    {
        db.collection("Invitations").document(uid)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}


