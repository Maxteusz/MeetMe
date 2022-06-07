package com.example.meetme.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Adapters.MyInvitiesRecyclerViewAdapter
import com.example.meetme.Adapters.SearchedInvitationsRecyclerViewAdapter
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Controllers.SearchedInvitationsFragmentController
import com.example.meetme.R

class SearchedInvitationsFragment : Fragment() {

    var recyclerView : RecyclerView? = null
    var adapter : SearchedInvitationsRecyclerViewAdapter? = null;
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_searched_invitations, container, false)
        val searchedInvitationsFragmentController = SearchedInvitationsFragmentController(this)
        recyclerView = view?.findViewById(R.id.serched_invitations_recyclerview)
        layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        adapter = SearchedInvitationsRecyclerViewAdapter(searchedInvitationsFragmentController.invitations ,searchedInvitationsFragmentController)
        recyclerView?.adapter = adapter

        return view



    }
}

