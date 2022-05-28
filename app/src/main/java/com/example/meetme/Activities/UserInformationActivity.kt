package com.example.meetme.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.Controllers.UserInformationController
import com.example.meetme.Models.User
import com.example.meetme.R
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.*


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
        userInformationController.DownloadImage().addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            imageView?.setImageBitmap(bmp)
        }
        imageView?.setOnClickListener { userInformationController.AddImage() }
        save_button = findViewById(R.id.save_button)

        var idDocument  = "";

        save_button?.setOnClickListener {
            userInformationController.SaveImage(imageView)
            if (idDocument == "")
            userInformationController.SaveUserData()
            else
                userInformationController.UpdateUserData(idDocument)

        }


        nick_TextView = findViewById(R.id.login_textfield)
        aboutMe_TextView = findViewById(R.id.aboutMe_textfield)

        userInformationController.ReadDataUser().addOnSuccessListener { value ->
            if(value.size() > 0) {
                val user = value.toObjects<User>()[0]
                idDocument = value.documents.first().id
                Log.i("Token", idDocument)
                userInformationController.FillActivity(user)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 3){
            imageView?.setImageURI(data?.data)
        }
    }
}