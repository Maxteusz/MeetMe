package com.example.meetme.Controllers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Activities.UserInformationActivity
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Fragments.RequestFragment
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.example.meetme.R
import com.example.meetme.Services.MyFirebaseMessagingService


class MenuActivityController {
    val menuActivity: MenuActivity




    constructor(menuActivity: MenuActivity) {
        this.menuActivity = menuActivity
        val sharedPreferences : SharedPreferences  = menuActivity.getSharedPreferences("_", MODE_PRIVATE)
        StartUpController.currentUser?.token = sharedPreferences.getString("FcmToken", "").toString()
        println("Token:" + StartUpController.currentUser?.token.toString())
    }

    fun addInvitedActivity() {
        val intent = Intent(menuActivity, NewInvitedActivity::class.java)
        menuActivity.startActivity(intent)
    }

    fun loadMyInvitationsFragment() {
        val fm = menuActivity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction
        val fragment = MyInvitedFragment()
        fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()
    }



    fun loadSearchInvitationsFragment() {
        val fm = menuActivity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction
        val fragment = SearchInvitationsFragment()
        fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()
    }

    fun loadFragment(id : Int)
    {
        val fm = menuActivity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction
        var fragment : Fragment? = null
        when (id) {
            R.id.myInvitations_page -> fragment = MyInvitedFragment()
            R.id.search_page -> fragment = SearchInvitationsFragment()
            R.id.requests_page -> fragment = RequestFragment()

        }
        fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment!!)

        fragmentTransaction.commit()
    }


}