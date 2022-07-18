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
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.google.type.DateTime


class SaveInvitedController {
    var newInvitedActivity: NewInvitedActivity
    private var fusedLocationClient: FusedLocationProviderClient
    private var loadingScreen : LoadingScreen = LoadingScreen("Dodawanie zaproszenia");


    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)

    }


    fun addInvited() {

        var currentLocation: GeoLocation?
        if(checkValidation())
        if (CountOfMyInvitations()!! <= 2) {
            loadingScreen.displayLoading(newInvitedActivity)
            if (isOnline(newInvitedActivity)) {
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
                    fusedLocationClient.getCurrentLocation(
                        PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                                return CancellationTokenSource().token
                            }
                            override fun isCancellationRequested(): Boolean {
                                return false
                            }
                        }

                    )
                        .addOnSuccessListener { location: android.location.Location? ->
                            if (location != null) {
                                currentLocation = GeoLocation(location.latitude, location.longitude)
                                val invited = createInvited(currentLocation!!)
                                if (invited != null)
                                    saveInvited(invited)

                            } else {
                                loadingScreen.hideLoading()
                                showSettingAlert()
                            }
                        }
                }
            }
        } else
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

    private fun saveInvited(invited: Invited) {
        try {
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
        catch (e :Exception) {
            loadingScreen.hideLoading()
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

