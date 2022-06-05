package com.example.meetme.Models

import com.example.meetme.Controllers.StartUpController
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation


class Invited {
    var uid: String? = null;
    var iHavePlace: Boolean? = false;
    var owner: User? = null;
    var place: String = "";
    var describe: String? = null
    var title: String = ""
    var longitude: Double = 0.0;
    var latitude: Double = 0.0;
    var kindOfAlcohol: String = ""
    var geohash: String = ""
    var memebers : List<User>? = null


    constructor()

    constructor(
        iHavePlace: Boolean?,
        place: String,
        describe: String?,
        title: String,
        longitude: Double,
        latitude: Double,
        kindOfAlcohol: String,
    ) {
        this.iHavePlace = iHavePlace
        this.owner = StartUpController.loggedUser
        if (this.iHavePlace == true && place.equals(""))
        this.place = "U mnie"
        else
            this.place = place
        this.describe = describe
        this.title = title
        this.longitude = longitude
        this.latitude = latitude
        this.kindOfAlcohol = kindOfAlcohol
        this.geohash = GeoFireUtils.getGeoHashForLocation(GeoLocation(latitude, longitude))
    }


}