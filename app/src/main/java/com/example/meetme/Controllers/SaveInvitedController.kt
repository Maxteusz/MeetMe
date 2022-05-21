package com.example.meetme.Controllers

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Controllers.StartUpController.Companion.currentUser
import com.example.meetme.Models.Invited
import com.example.meetme.Dialogs.LoadingScreen
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Models.Location
import com.example.meetme.Models.User
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SaveInvitedController {
    var newInvitedActivity: NewInvitedActivity
    private var fusedLocationClient: FusedLocationProviderClient



    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)


    }

    fun addInvited() {
        var currentLocation: Location?
        if(CountOfMyInvitations()!! <= 2)
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
                            saveInvited(createInvited(currentLocation!!), loadingScreen)

                        } else {
                            loadingScreen.hideLoading()
                            showSettingAlert()
                        }
                    }


        }
        else
            showDialogBox("Możesz mieć maksymalnie 3 zaproszenia")
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

    private fun saveInvited(invited: Invited, loadingScreen : LoadingScreen) {
        //Zapisywanie do Firebase Realtime
        /*var database = FirebaseDatabase.getInstance("https://meetme-5a1e5-default-rtdb.firebaseio.com")
        var myRef: DatabaseReference;
        myRef = database.getReference("Invitations").push()
        myRef.setValue(invited)*/

        val db = Firebase.firestore
        db.collection("Invitations")
            .add(invited)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                loadingScreen.hideLoading()
                showDialogBox("Zaproszenie zostało dodane")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                loadingScreen.hideLoading()
                showSettingAlert()
            }



    }

    private fun createInvited(location: Location): Invited {


        val iHavePlace = newInvitedActivity.havePlaceToDrink?.isChecked
        val place = ""
        val describe = newInvitedActivity.describe_textfield?.text.toString()
        val title = newInvitedActivity.title_textfield?.text.toString()
        val alcohol = newInvitedActivity.spinner_alcokohol?.text.toString()
        val owner = StartUpController.loggedUser
        val invited = Invited(iHavePlace, place, describe, title, alcohol, location,owner)
        return invited
    }

   private fun showDialogBox(message: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(newInvitedActivity).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "OK",

            { dialog, which ->
                if(message.equals("Zaproszenie zostało dodane")) {
                    dialog.dismiss()
                    newInvitedActivity.finish()
                }
                else
                    dialog.dismiss()

            })
        alertDialog.show()
    }

    private fun showSettingAlert() {
        val alertDialog = AlertDialog.Builder(newInvitedActivity)
        alertDialog.setTitle("Ustawienia GPS")
        alertDialog.setMessage("Czy przejść do ustawień GPS ?")
        alertDialog.setPositiveButton(
            "Tak"
        ) { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            newInvitedActivity.startActivity(intent)
        }
        alertDialog.setNegativeButton(
            "Nie"
        ) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    private fun CountOfMyInvitations() : Int?
    {
        return MyInvitedFragment.invitations?.size
    }


}

