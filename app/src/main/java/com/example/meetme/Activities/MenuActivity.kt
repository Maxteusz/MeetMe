package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.meetme.Controllers.MenuActivityController
import com.example.meetme.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class MenuActivity : AppCompatActivity() {
    var floatingButton : FloatingActionButton? = null
    var bottomNavigationView : BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val menuActivityController = MenuActivityController(this)
        menuActivityController.loadMyInvitationsFragment()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            item ->
            when(item.itemId) {
                R.id.myInvitations_page -> {
                   menuActivityController.loadMyInvitationsFragment()
                    true
                }
                R.id.search_page -> {
                    menuActivityController.loadSearchInvitationsFragment()
                    true
                }
                else -> false
            }
        })

        floatingButton = findViewById(R.id.floating_button)
        floatingButton?.setOnClickListener({
            menuActivityController.addInvitedActivity()
        })


    }



}