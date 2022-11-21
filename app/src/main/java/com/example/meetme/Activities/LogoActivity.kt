package com.example.meetme.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.R
import com.example.meetme.Services.MyFirebaseMessagingService


class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

            val startUpController = StartUpController(this)
            startUpController.nextActivity()

    }

}