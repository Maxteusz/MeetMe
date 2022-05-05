package com.example.meetme.Models

import android.location.Location
import com.example.meetme.Controllers.StartUpController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Invited {
     var uid : String?;
    var iHavePlace : Boolean = false;
    lateinit var owner : User;
    lateinit var place : String;
    var describe : String? = null
    lateinit var title : String
    lateinit var location : com.example.meetme.Models.Location
    lateinit var kindOfAlcohol : String


    constructor(
        uid: String?,
        iHavePlace: Boolean,
        owner: User,
        place: String,
        describe: String?,
        title: String,
        location: com.example.meetme.Models.Location,
        kindOfAlcohol: String
    ) {
        this.uid = uid
        this.iHavePlace = iHavePlace
        this.owner = owner
        this.place = place
        this.describe = describe
        this.title = title
        this.location = location
        this.kindOfAlcohol = kindOfAlcohol
    }



}