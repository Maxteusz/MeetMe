package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.meetme.Controllers.SaveInvitedController
import com.example.meetme.Models.Location
import com.example.meetme.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewInvitedActivity : AppCompatActivity() {
    var spinner_alcokohol : AutoCompleteTextView? = null
    var describe_textfield : TextInputEditText? = null
    var title_textfield : TextInputEditText? = null
    var havePlaceToDrink: CheckBox? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        spinner_alcokohol = findViewById(R.id.alcohol_list)
        val alcohol_array = resources.getStringArray(R.array.alcohol_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, alcohol_array)
        describe_textfield  = findViewById(R.id.describe_textfield)
        title_textfield  = findViewById(R.id.title_textfield)
        val addInvited : Button = findViewById(R.id.button3)
        val saveInvitedController = SaveInvitedController(this)


        spinner_alcokohol?.setAdapter(adapter)
         havePlaceToDrink = findViewById(R.id.have_place)


      addInvited.setOnClickListener(View.OnClickListener {
          saveInvitedController.addInvited()
      })



    }
}


