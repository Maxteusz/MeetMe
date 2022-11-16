package com.example.meetme.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Adapters.MyInvitiesRecyclerViewAdapter
import com.example.meetme.Adapters.RequestsRecyclerViewAdapter
import com.example.meetme.Adapters.SearchedInvitationsRecyclerViewAdapter
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Controllers.RequestFragmentController
import com.example.meetme.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class RequestFragment : Fragment() {


var requestFragmentController : RequestFragmentController? = null
    var recyclerView : RecyclerView? = null;
    var layoutManager : LinearLayoutManager? = null
    var adapter : RequestsRecyclerViewAdapter? = null;

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_request, container, false)
        requestFragmentController = RequestFragmentController(this)
        recyclerView = view.findViewById(R.id.request_page_recycler_view)
        layoutManager = LinearLayoutManager(context)

        recyclerView?.layoutManager = layoutManager
        var requests = requestFragmentController!!.getRequests()
        adapter = RequestsRecyclerViewAdapter(requests)
        Log.i("Requests", requests.size.toString())

        recyclerView?.adapter = adapter

        return view
    }



}