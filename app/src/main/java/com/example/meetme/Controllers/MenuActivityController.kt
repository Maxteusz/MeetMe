package com.example.meetme.Controllers

import android.content.Intent
import android.util.Log
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MenuActivityController {
    val menuActivity : MenuActivity

    companion object {

        fun  getRegistrationToken() : String
        {
            val refreshedToken = FirebaseMessaging.getInstance().token.toString();
            Log.i("Token", refreshedToken)
            return refreshedToken
        }
    }


    constructor(menuActivity: MenuActivity) {
        this.menuActivity = menuActivity
    }

    fun addInvitedActivity ()
    {
        val intent = Intent(menuActivity, NewInvitedActivity::class.java)
       menuActivity!!.startActivity(intent)
    }





}