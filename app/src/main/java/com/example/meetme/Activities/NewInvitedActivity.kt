package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.meetme.Controllers.SaveInvitedController
import com.example.meetme.R

class NewInvitedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val spinner_alcokohol : AutoCompleteTextView = findViewById<AutoCompleteTextView?>(R.id.alcohol_list)
        val alcohol_array = resources.getStringArray(R.array.alcohol_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, alcohol_array)
        spinner_alcokohol.setAdapter(adapter)
        val saveInvitedController = SaveInvitedController(this)
        saveInvitedController.saveTest()
    }
}


