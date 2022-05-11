package com.example.meetme.Controllers

import android.util.Log
import android.widget.Toast
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Models.Invited
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MyInvitationsFragmentController {
    val myInvitationsFragment : MyInvitedFragment

    private var database: DatabaseReference

    constructor(myInvitationsFragment: MyInvitedFragment) {
        this.myInvitationsFragment = myInvitationsFragment
        database = Firebase.database.getReference("invited")
    }

    fun getMyInvitations() : List<Invited>
    {
        var invitations : MutableList<Invited> = ArrayList()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val invited: Invited? = snapshot.child(item.key!!).getValue(Invited::class.java)
                    invitations.add(invited!!)
                    Log.i("Pobrano", invited?.place.toString())


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
        })

        return invitations
    }
}