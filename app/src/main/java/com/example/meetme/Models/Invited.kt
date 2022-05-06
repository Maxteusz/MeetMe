package com.example.meetme.Models



class Invited {
     var uid : String? = null;
    var iHavePlace : Boolean? = false;
    lateinit var owner : User;
    var place : String;
    var describe : String? = null
    var title : String
    var location : Location? = null
    var kindOfAlcohol : String


    constructor(
        uid: String?,
        iHavePlace: Boolean,
        owner: User,
        place: String,
        describe: String?,
        title: String,
        location: Location?,
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

    constructor(
        iHavePlace: Boolean?,
        place: String,
        describe: String?,
        title: String,
        kindOfAlcohol: String,
    ) {
        this.iHavePlace = iHavePlace
        this.place = place
        this.describe = describe
        this.title = title
        this.kindOfAlcohol = kindOfAlcohol
    }


}