package com.example.meetme.Controllers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentTransaction
import com.example.meetme.Dialogs.Dialogs
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.example.meetme.Fragments.SearchedInvitationsFragment
import com.example.meetme.Models.Invited
import com.example.meetme.R
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class SearchInvitationsFragmentController {
    val searchInvitationsFragment: SearchInvitationsFragment
    private var fusedLocationClient: FusedLocationProviderClient



    constructor(searchInvitationsFragment: SearchInvitationsFragment) {
        this.searchInvitationsFragment = searchInvitationsFragment
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(searchInvitationsFragment.requireContext())
    }

    fun getCurrentLocation()  = run {
        if (ActivityCompat.checkSelfPermission(
                searchInvitationsFragment.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                searchInvitationsFragment.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                searchInvitationsFragment.context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
          111
            );

        }

        fusedLocationClient.getCurrentLocation( Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken()
        {
            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                return CancellationTokenSource().token
            }

            override fun isCancellationRequested(): Boolean {
                return false
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SuspiciousIndentation")
    fun searchInvitations() {
        if(!checkEnableLocalization())
            return
        val loadingDialog : Dialogs.LoadingDialog = Dialogs.LoadingDialog(searchInvitationsFragment.requireContext())
        loadingDialog.show("Szukanie wydarzeń...", {})

        getCurrentLocation().addOnSuccessListener {
            if (it.latitude == null || it.longitude == null)
                return@addOnSuccessListener
            val center = GeoLocation(it.latitude, it.longitude)
            val radiusInM = (500000 * 1000).toDouble()
            val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
            val db = Firebase.firestore
            val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()
                    for (b in bounds) {
                        val q: Query = db.collection("Invitations")
                            .orderBy("geohash")
                            .startAt(b.startHash)
                            .endAt(b.endHash)
                        tasks.add(q.get())

            }
            Tasks.whenAllComplete(tasks)
                .addOnCompleteListener {
                    val invitations: MutableList<Invited> = ArrayList()
                    invitations.clear()
                    for (task in tasks) {
                        val snap = task.result
                        for (doc in snap.documents) {
                            val lat = doc.getDouble("latitude")!!
                            val lng = doc.getDouble("longitude")!!


                            val docLocation = GeoLocation(lat, lng)
                            val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                            if (distanceInM <= radiusInM) {
                                val invited = doc.toObject<Invited>()
                                if (invited?.owner?.uid != StartUpController.currentUser?.uid)
                                invitations.add(invited!!)
                            }
                        }
                    }
                    loadingDialog.hide()
                   openSearchedInvitationsFragment(invitations as ArrayList<Invited>)
                }
                .addOnFailureListener {
                    val informationDialog : Dialogs.InformationDialog = Dialogs.InformationDialog(searchInvitationsFragment.requireContext())
                informationDialog.show("Wystąpił błąd", {})}
        }
        getCurrentLocation().addOnFailureListener({loadingDialog.hide()})



    }



    private fun openSearchedInvitationsFragment(invitations : ArrayList<Invited>)
    {
        val bundle = Bundle ()
        bundle.putSerializable("invitations", invitations)
        val activity = searchInvitationsFragment.activity
        val fm =    activity?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction
        val fragment = SearchedInvitationsFragment()
        fragment.arguments = bundle
        fragmentTransaction = fm?.beginTransaction()!!
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)

        fragmentTransaction.commit()

    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkEnableLocalization () : Boolean
    {
        val context : Context? = null
        val locationManager = searchInvitationsFragment.activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isLocationEnabled
    }

}