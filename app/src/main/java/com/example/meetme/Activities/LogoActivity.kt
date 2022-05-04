package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.R

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)
       // val startUpController = StartUpController(this, menuActivity = MenuActivity(), mainActivity = MainActivity())
       // startUpController.nextActivity()
    }
}