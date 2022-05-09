package com.example.meetme.Controllers

import android.content.Context
import android.content.Intent
import com.example.meetme.Activities.LogoActivity
import com.example.meetme.Activities.MainActivity
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartUpController {
    var  logoActivity : LogoActivity
    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var currentUser = auth.currentUser
        lateinit var loggedUser: User
        fun getCurrentUser (context : Context) : User
        {

            val user =  User(currentUser!!.uid, "Maxteusz", User.Sex.Male, "fdfdfd",
                MenuActivityController.getRegistrationToken(context)
            )
            return user
        }
    }

    constructor(logoActivity: LogoActivity) {
        this.logoActivity = logoActivity

    }

    private fun isUserLogged () : Boolean
    {
        if(currentUser != null)
            return true;
        return false
    }

    fun nextActivity ()
    {
         var intent : Intent;
        if(isUserLogged()) {
            loggedUser = getCurrentUser(logoActivity)
            intent = Intent(logoActivity, MenuActivity::class.java)
        }
        else
             intent = Intent(logoActivity, MainActivity::class.java)
        logoActivity.startActivity(intent)
    }




}