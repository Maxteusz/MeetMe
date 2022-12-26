package com.example.meetme.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Adapters.MyInvitiesRecyclerViewAdapter
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Models.Invited
import com.example.meetme.R


class MyInvitationsFragment : Fragment() {

    var recyclerView : RecyclerView? = null
    var recyclerView2 : RecyclerView? = null
    var adapter : MyInvitiesRecyclerViewAdapter? = null;

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var layoutManager2: RecyclerView.LayoutManager? = null
    companion object{
        var myInvitations : MutableList<Invited>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_invited, container, false)
        val myInvitedFragmentController = MyInvitationsFragmentController(this)
        layoutManager = LinearLayoutManager(context)
        layoutManager2 = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.isNestedScrollingEnabled = false
        recyclerView?.layoutManager = layoutManager

        recyclerView2 = view.findViewById(R.id.recycler_view2)
        recyclerView2?.isNestedScrollingEnabled = false
        recyclerView2?.layoutManager = layoutManager2

        myInvitations = myInvitedFragmentController.getMyInvitations()

        adapter = MyInvitiesRecyclerViewAdapter(myInvitations!!, myInvitedFragmentController)

        recyclerView2?.adapter = adapter
        recyclerView?.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }
}
