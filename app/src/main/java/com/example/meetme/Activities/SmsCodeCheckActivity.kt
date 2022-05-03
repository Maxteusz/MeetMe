package com.example.meetme.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.meetme.Controllers.RegistartionController
import com.example.meetme.R
import com.google.android.material.textfield.TextInputEditText


class SmsCodeCheckActivity : AppCompatActivity() {
    var phoneNumber = ""
    var loadingProgressBar : ProgressBar? = null
    var respondCodeTextView : TextInputEditText? = null
    var button : Button? = null
    lateinit var registration : RegistartionController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_code_check)
        loadingProgressBar = findViewById(R.id.progressBar2)
        respondCodeTextView = findViewById(R.id.textField)
        button = findViewById(R.id.button2)

        val intent= intent
        val bundle = intent.extras
        if(bundle != null) {
            phoneNumber = "+48" + bundle.get("PHONE_NUMBER")!!
            Log.i("SmsCOdeActivity", phoneNumber)
            registration = RegistartionController(this, phoneNumber)
            registration.SendVeryficationCode()
        }

        button?.setOnClickListener(View.OnClickListener {
            //registration.signInWithPhoneAuthCredential()
            registration = RegistartionController(this, phoneNumber)
            registration.SendVeryficationCode()
        })

    }
}