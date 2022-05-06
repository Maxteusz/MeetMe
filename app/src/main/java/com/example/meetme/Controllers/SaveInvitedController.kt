package com.example.meetme.Controllers

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Models.Invited
import com.example.meetme.Dialogs.LoadingScreen
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



    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)
    }

    fun addInvited() {
        var currentLocation: Location?
        if (ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                newInvitedActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showPermissionAlert();
            return
        } else {
            val loadingScreen = LoadingScreen()
            loadingScreen.displayLoading(newInvitedActivity)
            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location: android.location.Location? ->
                    if (location != null) {
                        currentLocation = Location(location.longitude, location.latitude)
                        saveInvited(createInvited(currentLocation!!))
                        loadingScreen.hideLoading()
                        showDialogBox("Zaproszenie zostało dodane")
                    }
                    else {
                        loadingScreen.hideLoading()
                        showSettingAlert()
                    }
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

    private fun saveInvited(invited: Invited) {

        myRef = database.getReference("invited").push()
        invited.owner =
            User(StartUpController.currentUser!!.uid, "Maxteusz", User.Sex.Male, "fdfdfd")
        this.myRef.setValue(invited)
    }

    private fun createInvited(location: Location): Invited {
        val iHavePlace = newInvitedActivity.havePlaceToDrink?.isChecked
        val place = "4343"
        val describe = newInvitedActivity.describe_textfield?.text.toString()
        val title = newInvitedActivity.title_textfield?.text.toString()
        val alcohol = newInvitedActivity.spinner_alcokohol?.text.toString()
        val invited = Invited(iHavePlace, place, describe, title, alcohol, location)
        return invited
    }

    fun showDialogBox(message: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(newInvitedActivity).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "OK",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss()})
        alertDialog.show()
    }

    fun showSettingAlert() {
        val alertDialog = AlertDialog.Builder(newInvitedActivity)
        alertDialog.setTitle("GPS setting!")
        alertDialog.setMessage("GPS nie jest włączony, proszę")
        alertDialog.setPositiveButton(
            "Setting"
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            newInvitedActivity.startActivity(intent)
        }
        alertDialog.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }
}

