package com.example.meetme.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.meetme.Controllers.UserInformationController
import com.example.meetme.R

class UserInformationActivity : AppCompatActivity() {
    var imageView : ImageView? = null
    var save_button : Button? = null
    var nick_TextView : TextView? = null
    var aboutMe_TextView : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        val userInformationController = UserInformationController(this)
        imageView = findViewById(R.id.user_image)
        imageView?.setOnClickListener { userInformationController.AddImage() }
        save_button = findViewById(R.id.save_button)
        save_button?.setOnClickListener {
            userInformationController.SaveData(imageView)
        }

        nick_TextView = findViewById(R.id.login_textfield)
        aboutMe_TextView = findViewById(R.id.aboutMe_textfield)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 3){
            imageView?.setImageURI(data?.data)
        }
    }
}