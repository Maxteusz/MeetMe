package com.example.meetme.Controllers

import android.content.Intent
import android.util.Log
import com.example.meetme.Activities.*
import com.example.meetme.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class StartUpController {
    var logoActivity: LogoActivity

    companion object {
        var auth: FirebaseAuth = Firebase.auth

        var currentUser: User? = User(auth)
        var db = Firebase.firestore
        fun ReadDataUser() = db.collection("Users")
            .whereEqualTo("uid", currentUser?.uid)
            .get()
    }

    constructor(logoActivity: LogoActivity) {
        this.logoActivity = logoActivity
        Log.i("Utworzono uzytkownika", currentUser?.uid.toString())

    }



    fun nextActivity() {
        var intent: Intent;
        Log.i("User", auth.currentUser?.uid.toString())
        if (currentUser?.isUserLogged() == true) {
            ReadDataUser().addOnSuccessListener { value ->
                if (value.size() > 0) {
                    val user = value.toObjects<User>()[0]
                   // currentUser = user;
                    intent = Intent(logoActivity, MenuActivity::class.java)
                    logoActivity.startActivity(intent)
                    logoActivity.finish()

                }

            }
                .addOnFailureListener { ex -> Log.i("fd", ex.toString()) }

        } else {
            intent = Intent(logoActivity, EntryPhoneActivity::class.java)
            logoActivity.startActivity(intent)
            logoActivity.finish()
        }
    }


}