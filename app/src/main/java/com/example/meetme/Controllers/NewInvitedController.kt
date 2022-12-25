package com.example.meetme.Controllers

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.util.Log
import com.example.meetme.Activities.NewInvitedActivity
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Location
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NewInvitedController {
    var newInvitedActivity: NewInvitedActivity
    private var fusedLocationClient: FusedLocationProviderClient
    private var location: Location
    private var loadingDialog: Dialogs.LoadingDialog


    constructor(newInvitedActivity: NewInvitedActivity) {
        this.newInvitedActivity = newInvitedActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(newInvitedActivity)
        location = Location(newInvitedActivity)
        loadingDialog = Dialogs.LoadingDialog(newInvitedActivity)

    }

    @SuppressLint("SuspiciousIndentation")
    fun addInvited() {
        if (countOfMyInvitations()!! == 3) {
            val informationDialog = Dialogs.InformationDialog(newInvitedActivity)
            informationDialog.show("Masz maksymalną ilość wydarzeń", {})
            return
        }
        if (checkValidation())
            if (isOnline(newInvitedActivity) && countOfMyInvitations()!! < 3) {
                newInvitedActivity.addInvited_button.isClickable = false
                loadingDialog.show("Dodawanie zlecenia", {})
                location.getLocation {

                    val invited = createInvited(GeoLocation(it.latitude, it.longitude))
                    saveInvited(invited!!)

                }
            }


    }

    private fun saveInvited(invited: Invited) {
        try {
            val dialog = Dialogs.InformationDialog(newInvitedActivity);

            val db = Firebase.firestore
            val ref = db.collection("Invitations").document().id
            invited.id = ref
            db.collection("Invitations").document(invited.id!!)
                .set(invited)
                .addOnSuccessListener { documentReference ->
                    loadingDialog.hide()
                    newInvitedActivity.addInvited_button.isClickable = true
                    loadingDialog.hide()
                    dialog.show("Utworzono zdarzenie", {
                        newInvitedActivity.finish()
                    })

                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    loadingDialog.hide()
                    newInvitedActivity.addInvited_button.isClickable = true
                    showSettingAlert()
                }
        }
        catch (e :Exception) {
            newInvitedActivity.addInvited_button.isClickable = true
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
        ) { dialog,_ -> dialog.cancel() }
        alertDialog.show()
    }

    private fun countOfMyInvitations(): Int? {
        return MyInvitedFragment.invitations?.size
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo?
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}

