package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.meetme.Controllers.MenuActivityController
import com.example.meetme.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    }


}