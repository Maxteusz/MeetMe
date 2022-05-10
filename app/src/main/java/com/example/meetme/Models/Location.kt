package com.example.meetme.Models

import android.location.Location
import android.location.LocationListener
import android.os.Parcel
import android.os.Parcelable

class Location  {

    var  latitude : Double = 0.0
    var  longtitude : Double = 0.0

    constructor(langtitude: Double, longtitude: Double) {
        this.latitude = langtitude
        this.longtitude = longtitude
    }
    constructor()
}
