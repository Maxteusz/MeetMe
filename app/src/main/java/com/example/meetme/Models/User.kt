package com.example.meetme.Models

class User {
    lateinit var uid : String;
    lateinit var nick : String;
    lateinit var sex : Sex;
    lateinit var aboutMe : String;

    constructor(uid: String, nick: String, sex: Sex, aboutMe: String) {
        this.uid = uid
        this.nick = nick
        this.sex = sex
        this.aboutMe = aboutMe
    }

    enum class Sex
    {
        Male,Female
    }


}

