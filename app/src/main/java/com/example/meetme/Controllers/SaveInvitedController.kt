package com.example.meetme.Controllers

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.Invited
import com.example.meetme.Models.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SaveInvitedController {
    lateinit var newInvitedActivity : NewInvitedActivity
    var database = FirebaseDatabase.getInstance("https://meetme-5a1e5-default-rtdb.firebaseio.com")
    var myRef: DatabaseReference;
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        myRef = database.getReference("invited").push()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)
    }

    private fun getLocation () : com.example.meetme.Models.Location
    {
        val currentLocation : com.example.meetme.Models.Location = com.example.meetme.Models.Location(0.0,0.0)
        if (ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        currentLocation.latitude = location.latitude
                        currentLocation.longtitude = location.longitude
                    }

                }

        }
        return currentLocation

    }

    fun saveTest ()
    {

        var user  = User(StartUpController.currentUser!!.uid,"Maxteusz", User.Sex.Male, "fdfdfd")

        var invited = Invited(null, true, user,"Dom",null, "Test" , getLocation(),"Wódka")
        this.myRef.setValue(invited)

    }
}