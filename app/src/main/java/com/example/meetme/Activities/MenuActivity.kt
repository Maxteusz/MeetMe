package com.example.meetme.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.meetme.Controllers.MenuActivityController
import com.example.meetme.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.android.gms.tasks.OnCompleteListener
import android.util.Log
import android.widget.Toast
import com.example.meetme.Services.MyFirebaseMessagingService
import com.google.firebase.messaging.FirebaseMessaging

class MenuActivity : AppCompatActivity() {
    var floatingButton : FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val menuActivityController = MenuActivityController(this)
        floatingButton = findViewById(R.id.floating_button)
        floatingButton?.setOnClickListener(View.OnClickListener {
            menuActivityController.addInvitedActivity()
        })
        //startTokenService()
    }
    private fun startTokenService()
    {
        Log.i("Start service", "ds")
        val intent = Intent(this, MyFirebaseMessagingService::class.java)
        startService(intent)
    }


}