package com.example.meetme

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class SmsCodeCheckActivity : AppCompatActivity() {
    var phoneNumber = ""
    var loadingProgressBar : ProgressBar? = null
    var respondCodeTextView : TextInputEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_code_check)
        loadingProgressBar = findViewById(R.id.progressBar2)
        respondCodeTextView = findViewById(R.id.textField)
       // loadingProgressBar?.visibility = View.VISIBLE
        val intent= intent
        val bundle = intent.extras
        if(bundle != null) {
            phoneNumber = "+48" + bundle.get("PHONE_NUMBER")!!
            Log.i("SmsCOdeActivity", phoneNumber)
            val registartion = RegistartionController(this, phoneNumber)
            registartion.SendVeryficationCode()
        }

    }
}