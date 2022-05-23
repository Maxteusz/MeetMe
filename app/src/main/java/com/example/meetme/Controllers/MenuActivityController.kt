package com.example.meetme.Controllers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Activities.UserInformationActivity
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.example.meetme.R


class MenuActivityController {
    val menuActivity: MenuActivity

    companion object {

        fun getRegistrationToken(context: Context): String {
            val refreshedToken =
                context.getSharedPreferences("_", MODE_PRIVATE).getString("FcmToken", "empty")
                    .toString()
            Log.i("Token", refreshedToken)
            return refreshedToken
        }
    }


    constructor(menuActivity: MenuActivity) {
        this.menuActivity = menuActivity
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
        fragmentTransaction.replace(R.id.fragment_container_view, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun loadSearchInvitationsFragment() {
        val fm = menuActivity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction
        val fragment = SearchInvitationsFragment()
        fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }


}