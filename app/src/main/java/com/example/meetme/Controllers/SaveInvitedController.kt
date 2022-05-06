package com.example.meetme.Controllers

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.Invited
import com.example.meetme.Models.LoadingScreen
import com.example.meetme.Models.Location
import com.example.meetme.Models.User
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SaveInvitedController {
    var newInvitedActivity: NewInvitedActivity
    var database = FirebaseDatabase.getInstance("https://meetme-5a1e5-default-rtdb.firebaseio.com")
    lateinit var myRef: DatabaseReference;
    private var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback



    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)
    }

  fun addInvited() {
      val loadingScreen = LoadingScreen()
      loadingScreen.displayLoading(newInvitedActivity)
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
            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location : android.location.Location? ->
                    if (location != null) {
                        currentLocation = Location(location.longitude, location.latitude)
                        saveInvited(createInvited(currentLocation!!))
                        loadingScreen.hideLoading()
                    }

                    Log.i("Location" , location?.latitude.toString())

                }

        }

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

   private  fun saveInvited(invited: Invited) {

        myRef = database.getReference("invited").push()
        invited.owner =
            User(StartUpController.currentUser!!.uid, "Maxteusz", User.Sex.Male, "fdfdfd")

        this.myRef.setValue(invited)



    }


   private fun createInvited (location: Location) : Invited
    {
        val iHavePlace = newInvitedActivity.havePlaceToDrink?.isChecked
        val place = "4343"
        val describe = newInvitedActivity.describe_textfield?.text.toString()
        val title = newInvitedActivity.title_textfield?.text.toString()
        val alcohol = newInvitedActivity.spinner_alcokohol?.text.toString()
        val invited = Invited(iHavePlace, place, describe, title, alcohol,location )
        return  invited
    }
}

