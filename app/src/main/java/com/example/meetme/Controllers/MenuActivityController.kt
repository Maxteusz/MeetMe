package com.example.meetme.Controllers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.User
import com.example.meetme.Services.MyFirebaseMessagingService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MenuActivityController {
    val menuActivity : MenuActivity

    companion object {

        fun  getRegistrationToken(context : Context) : String
        {
            val refreshedToken =   context.getSharedPreferences("_", MODE_PRIVATE).getString("FcmToken", "empty").toString()
            Log.i("Token", refreshedToken.toString())
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