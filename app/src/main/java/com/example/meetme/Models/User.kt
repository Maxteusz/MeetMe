package com.example.meetme.Models

class User {
    var uid : String;
    var nick : String;
    var sex : Sex;
    var aboutMe : String;

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

