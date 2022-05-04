package com.example.meetme.Controllers

import android.content.Intent
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
    var  mainActivity: MainActivity
    var menuActivity : MenuActivity
    private var auth: FirebaseAuth = Firebase.auth

    constructor(logoActivity: LogoActivity, mainActivity: MainActivity, menuActivity : MenuActivity) {
        this.logoActivity = logoActivity
        this.mainActivity = mainActivity
        this.menuActivity = menuActivity
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
        lateinit var intent : Intent;
        if(isUserLogged()) {
            val intent = Intent(logoActivity, mainActivity::class.java)
        }
        else
             intent = Intent(logoActivity, mainActivity::class.java)
        logoActivity.startActivity(intent)
    }


}