package com.example.meetme.Controllers

import com.example.meetme.Fragments.SearchedInvitationsFragment
import com.example.meetme.Models.Invited
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class SearchedInvitationsFragmentController {

    val searchedInvitationsFragment : SearchedInvitationsFragment
    val invitations : ArrayList<Invited>
    var refStorage = FirebaseStorage.getInstance().reference

    constructor(searchedInvitationsFragment: SearchedInvitationsFragment) {
        this.searchedInvitationsFragment = searchedInvitationsFragment
         val invitations : ArrayList<Invited> = searchedInvitationsFragment.arguments?.getSerializable("invitations") as ArrayList<Invited>
        this.invitations =  invitations
    }

    fun DownloadImage (uidUser : String) = refStorage.child(uidUser).getBytes(1024 * 1024 *10)
}