package com.example.meetme.Models

import android.location.Location
import android.location.LocationListener
import android.os.Parcel
import android.os.Parcelable

class Location  {

    var  latitude : Double
    var  longtitude : Double

    constructor(langtitude: Double, longtitude: Double) {
        this.latitude = langtitude
        this.longtitude = longtitude
    }
}
