package com.example.meetme.Controllers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Location
import com.example.meetme.Models.User
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationToken
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SaveInvitedController {
    var newInvitedActivity: NewInvitedActivity
    var database = FirebaseDatabase.getInstance("https://meetme-5a1e5-default-rtdb.firebaseio.com")
    lateinit var myRef: DatabaseReference;
    private var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    lateinit var invited : Invited


    constructor(newInvitedActivity: NewInvitedActivity, invited: Invited) {
        this.invited = invited
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)
    }

    fun getLocation(): com.example.meetme.Models.Location? {
        var currentLocation: com.example.meetme.Models.Location? = null
        if (ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showPermissionAlert();
        } else {
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location : android.location.Location? ->
                    currentLocation?.longtitude = location?.longitude?.toDouble()!!
                    currentLocation?.latitude = location?.latitude?.toDouble()!!
                    saveTest()
                    Log.i("Location" , location?.latitude.toString())

                }
        }
        return currentLocation
    }

    private fun showPermissionAlert() {
        if (ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                newInvitedActivity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                123
            );
        }
    }

    fun saveTest() {
        myRef = database.getReference("invited").push()
        invited.owner =
            User(StartUpController.currentUser!!.uid, "Maxteusz", User.Sex.Male, "fdfdfd")
        this.myRef.setValue(invited)


    }

    fun createInvited () : Invited
    {
        val iHavePlace = newInvitedActivity.havePlaceToDrink?.isChecked
        val place = "4343"
        val describe = newInvitedActivity.describe_textfield?.text.toString()
        val title = newInvitedActivity.title_textfield?.text.toString()
        val alcohol = newInvitedActivity.spinner_alcokohol?.text.toString()
        val invited = Invited(iHavePlace, place, describe, title, alcohol)
        return  invited
    }
}

