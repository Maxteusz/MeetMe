package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val EPhoneNumber : EditText = findViewById(R.id.edtiText)
        val buttonTest : Button = findViewById(R.id.button);
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
           Log.i("zalogowany", "zalogowany")
        }


        buttonTest.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SmsCodeCheckActivity::class.java).apply {
                putExtra("PHONE_NUMBER", EPhoneNumber.text)
            }
            startActivity(intent)
            //finish()
        })

    }
}