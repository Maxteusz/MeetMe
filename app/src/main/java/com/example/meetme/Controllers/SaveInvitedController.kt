package com.example.meetme.Controllers

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Dialogs.LoadingScreen
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Location
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SaveInvitedController {
    var newInvitedActivity: NewInvitedActivity
    private var fusedLocationClient: FusedLocationProviderClient
    private var loadingScreen : LoadingScreen = LoadingScreen("Dodawanie zaproszenia");
    private var location : Location


    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)
        location = Location(newInvitedActivity)

    }

  fun addInvited()
    {
        newInvitedActivity.addInvited_button?.isClickable = false
        if(isOnline(newInvitedActivity)) {
            location.getLocation {
                val invited = createInvited(GeoLocation(it.latitude, it.longitude))
                if (CountOfMyInvitations()!! <= 2)
                    if (checkValidation())
                        saveInvited(invited!!)
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
        try {

            val db = Firebase.firestore
            val ref = db.collection("Invitations").document().id
            invited.uid = ref
            db.collection("Invitations")
                .add(invited)
                .addOnSuccessListener { documentReference ->
                    loadingScreen.hideLoading()
                    newInvitedActivity.addInvited_button?.isClickable = true
                    showDialogBox("Zaproszenie zostało dodane")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    loadingScreen.hideLoading()
                    newInvitedActivity.addInvited_button?.isClickable = true
                    showSettingAlert()
                }
        }
        catch (e :Exception) {
            loadingScreen.hideLoading()
            newInvitedActivity.addInvited_button?.isClickable = true
            Log.i("Exception", e.toString())
        }


    }

    fun checkValidation () : Boolean
    {
        if (newInvitedActivity.title_textfield?.text.toString() == "") {
            newInvitedActivity.title_textfield?.error = "Tytuł jest wymagany"
            return false
        }
        return true;


    }

    private fun createInvited(location: GeoLocation): Invited? {
            val iHavePlace = newInvitedActivity.havePlaceToDrink?.isChecked
            val place = ""
            val describe = newInvitedActivity.describe_textfield?.text.toString()
            val title = newInvitedActivity.title_textfield?.text.toString()
            val alcohol = newInvitedActivity.spinner_alcokohol?.text.toString()
            val invited = Invited(
                iHavePlace,
                place,
                describe,
                title,
                location.longitude,
                location.latitude,
                alcohol
            )
            return invited

    }

    private fun showDialogBox(message: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(newInvitedActivity).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "OK",

            { dialog, which ->
                if (message.equals("Zaproszenie zostało dodane")) {
                    dialog.dismiss()
                    newInvitedActivity.finish()
                } else
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

    private fun CountOfMyInvitations(): Int? {
        return MyInvitedFragment.invitations?.size
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo?
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}

