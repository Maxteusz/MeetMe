package com.example.meetme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.meetme.Controllers.NewInvitedController
import com.example.meetme.R
import com.google.android.material.textfield.TextInputEditText

class NewInvitedActivity : AppCompatActivity() {
    var spinner_alcokohol: AutoCompleteTextView? = null
    var describe_textfield: TextInputEditText? = null
    var title_textfield: TextInputEditText? = null
    var havePlaceToDrink: CheckBox? = null
    lateinit var addInvited_button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_invited)
        spinner_alcokohol = findViewById(R.id.alcohol_list)
        val alcohol_array = resources.getStringArray(R.array.alcohol_array)
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, alcohol_array)
        describe_textfield = findViewById(R.id.describe_textfield)
        title_textfield = findViewById(R.id.title_textfield)
         addInvited_button = findViewById(R.id.add_button)
        val newInvitedController = NewInvitedController(this)

        spinner_alcokohol?.setAdapter(adapter)
        havePlaceToDrink = findViewById(R.id.have_place)


        addInvited_button.setOnClickListener({
            newInvitedController.addInvited()
        })


    }
}


