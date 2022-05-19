package com.example.meetme.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.meetme.Controllers.SearchInvitationsFragmentController
import com.example.meetme.R

class SearchInvitationsFragment : Fragment() {

var search_button : Button? = null;
    var searchInvitationsFragmentController : SearchInvitationsFragmentController? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_search_invitations, container, false)
        searchInvitationsFragmentController = SearchInvitationsFragmentController(this)

        search_button = view.findViewById(R.id.search_button)
        search_button?.setOnClickListener {view ->
            searchInvitationsFragmentController?.searchInvitations()
        }

        return view
    }


}