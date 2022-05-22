package com.example.meetme.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Adapters.MyInvitiesRecyclerViewAdapter
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Models.Invited
import com.example.meetme.R


class MyInvitedFragment : Fragment() {

    var recyclerView : RecyclerView? = null
    var adapter : MyInvitiesRecyclerViewAdapter? = null;
    private var layoutManager: RecyclerView.LayoutManager? = null
    companion object{
        var invitations : MutableList<Invited>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_invited, container, false)
        val myInvitedFragmentController = MyInvitationsFragmentController(this)
        recyclerView = view.findViewById(R.id.recycler_view)
        layoutManager = LinearLayoutManager(context)
        invitations = myInvitedFragmentController.invitations
        recyclerView?.layoutManager = layoutManager
        adapter = MyInvitiesRecyclerViewAdapter(invitations!!, myInvitedFragmentController)
        recyclerView?.adapter = adapter
        return view
    }

}
