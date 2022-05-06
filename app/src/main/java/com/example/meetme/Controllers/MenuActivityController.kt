package com.example.meetme.Controllers

import android.content.Intent
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.NewInvitedActivity

class MenuActivityController {
    val menuActivity : MenuActivity

    constructor(menuActivity: MenuActivity) {
        this.menuActivity = menuActivity
    }

    fun addInvitedActivity ()
    {
        val intent = Intent(menuActivity, NewInvitedActivity::class.java)
       menuActivity!!.startActivity(intent)
    }
}