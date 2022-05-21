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
       /* database.orderByChild("owner/uid").equalTo(StartUpController.loggedUser.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                invitations.clear()
                for (item in snapshot.children) {
                    val invited: Invited? = snapshot.child(item.key!!).getValue(Invited::class.java)
                    invitations.add(invited!!)
                    Log.i("Pobrano", invited.place)
                }
                Log.i("Rozmiar", invitations.size.toString())
                myInvitationsFragment.adapter?.notifyDataSetChanged()
                Log.i("Adapter Controller", myInvitationsFragment.adapter?.itemCount.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(myInvitationsFragment.context, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })*/

        db.collection("Invitations")
            .whereEqualTo(FieldPath.of("owner", "uid"),StartUpController.loggedUser.uid)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                invitations.clear()
                for (doc in value!!) {
                        invitations.add(doc.toObject())
                    }

                myInvitationsFragment.adapter?.notifyDataSetChanged()

            }
        return invitations
    }
}


