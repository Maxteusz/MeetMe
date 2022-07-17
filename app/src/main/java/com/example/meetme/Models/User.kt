package com.example.meetme.Models


import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable

class User : Serializable {
    var uid : String = "";
    var nick : String = "";
    var aboutMe : String = "";
    var token : String = ""
    var auth : FirebaseAuth? = null;

    constructor(uid: String, nick: String, aboutMe: String, token : String) {
        this.uid = uid
        this.nick = nick
        this.aboutMe = aboutMe
        this.token = token
    }

    constructor(auth : FirebaseAuth)
    {
        this.auth = auth
        this.uid = auth.currentUser?.uid.toString()
    }

    fun isUserLogged() : Boolean
    {
        if(auth?.currentUser == null)
            return false;
        return true;
    }

    enum class Sex
    {
        None,Male,Female
    }
    constructor()

}

