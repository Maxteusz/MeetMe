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
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.Models.Invited
import com.example.meetme.R

class MyInvitedFragment : Fragment() {

    var recyclerView : RecyclerView? = null
    var adapter : MyInvitiesRecyclerViewAdapter? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerView = container?.findViewById(R.id.recycler_view)
        return inflater.inflate(R.layout.fragment_my_invited, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myInvitedFragmentController = MyInvitationsFragmentController(this)


         adapter = MyInvitiesRecyclerViewAdapter(myInvitedFragmentController.getMyInvitations())
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

    }
}
