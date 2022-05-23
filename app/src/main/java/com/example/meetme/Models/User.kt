package com.example.meetme.Models

import android.widget.ImageView

class User {
    var uid : String = "";
    var nick : String = "";
    var sex : Sex = Sex.None;
    var aboutMe : String = "";
    var token : String = ""
    var image : ImageView? = null

    constructor(uid: String, nick: String, aboutMe: String, token : String) {
        this.uid = uid
        this.nick = nick
        this.aboutMe = aboutMe
        this.token = token
    }



    enum class Sex
    {
        None,Male,Female
    }
    constructor()

    constructor(
        uid: String,
        nick: String,
        sex: Sex,
        aboutMe: String,
        token: String,
        image: ImageView?
    ) {
        this.uid = uid
        this.nick = nick
        this.sex = sex
        this.aboutMe = aboutMe
        this.token = token
        this.image = image
    }

}

