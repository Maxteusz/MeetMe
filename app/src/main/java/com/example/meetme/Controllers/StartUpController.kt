package com.example.meetme.Controllers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.meetme.Activities.*
import com.example.meetme.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class StartUpController {
    var logoActivity: LogoActivity

    companion object {
        var auth: FirebaseAuth = Firebase.auth
        var firebaseUser = auth.currentUser
        var loggedUser: User? = User()
        var db = Firebase.firestore
        fun ReadDataUser() = db.collection("Users")
            .whereEqualTo("uid", StartUpController.firebaseUser?.uid)
            .get()
    }

    constructor(logoActivity: LogoActivity) {
        this.logoActivity = logoActivity

    }

    private fun isUserLogged(): Boolean {
        if (firebaseUser != null)
            return true;
        return false
    }

    fun nextActivity() {
        var intent: Intent;
        if (isUserLogged()) {
            ReadDataUser().addOnSuccessListener { value ->
                if (value.size() > 0) {
                    val user = value.toObjects<User>()[0]
                    loggedUser = user;
                    intent = Intent(logoActivity, MenuActivity::class.java)
                    logoActivity.startActivity(intent)
                    logoActivity.finish()
                    Log.i("User", loggedUser?.nick!!)
                }
            }

        } else {
            intent = Intent(logoActivity, EntryPhoneActivity::class.java)
            logoActivity.startActivity(intent)
            logoActivity.finish()
        }
    }


}