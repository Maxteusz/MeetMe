package com.example.meetme.Controllers

import com.example.meetme.Fragments.SearchedInvitationsFragment
import com.example.meetme.Models.Invited
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class SearchedInvitationsFragmentController {

    val searchedInvitationsFragment : SearchedInvitationsFragment
    val invitations : Array<Invited>
    var refStorage = FirebaseStorage.getInstance().reference

    constructor(searchedInvitationsFragment: SearchedInvitationsFragment) {
        this.searchedInvitationsFragment = searchedInvitationsFragment
         val _invitations  = searchedInvitationsFragment.arguments?.getSerializable("invitations") as List<Invited>
        this.invitations =  _invitations.sortedBy { it.title}.toTypedArray()/* = java.util.ArrayList<com.example.meetme.Models.Invited> */
    }

    fun downloadImage (uidUser : String) = refStorage.child(uidUser).getBytes(1024 * 1024 *10)
}