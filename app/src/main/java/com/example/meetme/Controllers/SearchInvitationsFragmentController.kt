package com.example.meetme.Controllers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.example.meetme.Models.Invited
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.logging.Level.INFO


class SearchInvitationsFragmentController {
    val searchInvitationsFragment : SearchInvitationsFragment
    private var database: DatabaseReference

    constructor(searchInvitationsFragment: SearchInvitationsFragment) {
        this.searchInvitationsFragment = searchInvitationsFragment
        database = Firebase.database.getReference("invited")
    }

     fun searchInvitations() : MutableList<Invited>
    {

        var invitations : MutableList<Invited> = ArrayList()
        database.orderByChild("title").equalTo("19").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {

                    Log.i("Znaleziono", postSnapshot.toString())
                }
                Log.i(" Znaleziono", "invited?.title.toString()")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                Log.i("Nie Znaleziono", "invited?.title.toString()")
            }
        })

        return invitations
    }


}