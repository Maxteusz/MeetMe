package com.example.meetme.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.meetme.Controllers.RequestFragmentController
import com.example.meetme.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : Fragment() {
    // TODO: Rename and change types of parameters

var requestFragmentController : RequestFragmentController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestFragmentController = RequestFragmentController(this)
        requestFragmentController!!.getRequests()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)
    }



}