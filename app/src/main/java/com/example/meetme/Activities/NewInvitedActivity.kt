package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meetme.Controllers.SaveInvitedController
import com.example.meetme.R

class NewInvitedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val saveInvitedController = SaveInvitedController(this)
        saveInvitedController.saveTest()
    }
}