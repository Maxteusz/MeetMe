package com.example.meetme.Controllers

import android.content.Intent
import android.util.Log
import android.view.Menu
import androidx.core.content.ContextCompat.startActivity
import com.example.meetme.Activities.LogoActivity
import com.example.meetme.Activities.MainActivity
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.SmsCodeCheckActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartUpController {
    var  logoActivity : LogoActivity

    private var auth: FirebaseAuth = Firebase.auth
    constructor(logoActivity: LogoActivity) {
        this.logoActivity = logoActivity

    }

    private fun isUserLogged () : Boolean
    {
        val currentUser = auth.currentUser
        if(currentUser != null)
            return true;
        return false
    }

    fun nextActivity ()
    {
         var intent : Intent;
        if(isUserLogged()) {
            intent = Intent(logoActivity, MenuActivity::class.java)
        }
        else
             intent = Intent(logoActivity, MainActivity::class.java)
        logoActivity.startActivity(intent)
    }


}