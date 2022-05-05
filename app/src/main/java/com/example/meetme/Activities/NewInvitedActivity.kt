package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.StartElementListener
import android.view.View
import android.widget.*
import com.example.meetme.Controllers.SaveInvitedController
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.Models.Invited
import com.example.meetme.R
import com.google.android.material.textfield.TextInputEditText

class NewInvitedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val spinner_alcokohol : AutoCompleteTextView = findViewById<AutoCompleteTextView?>(R.id.alcohol_list)
        val alcohol_array = resources.getStringArray(R.array.alcohol_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, alcohol_array)
        val describe_textfield : TextInputEditText = findViewById(R.id.describe_textfield)
        val title_textfield : TextInputEditText = findViewById(R.id.title_textfield)
        val addInvited : Button = findViewById(R.id.button3)
        spinner_alcokohol.setAdapter(adapter)
        val havePlaceToDrink: CheckBox = findViewById(R.id.have_place)


      addInvited.setOnClickListener(View.OnClickListener {
          val saveInvitedController = SaveInvitedController(this)
          val invited = Invited(
              havePlaceToDrink.isChecked,
              "Dom",
              describe_textfield.text.toString(),
              title_textfield.text.toString(),
              spinner_alcokohol.text.toString()
          )
          saveInvitedController.saveTest(invited)
      })
    }
}


