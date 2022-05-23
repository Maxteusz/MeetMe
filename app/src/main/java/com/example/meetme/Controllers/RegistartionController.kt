package com.example.meetme.Controllers

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.View
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.SmsCodeCheckActivity
import com.example.meetme.Activities.UserInformationActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.concurrent.TimeUnit


class RegistartionController {

    var smsCodeCheckActivity: SmsCodeCheckActivity? = null
    var phoneNumber: String = "";
    private lateinit var verificationID: String
    private lateinit var respondToken: PhoneAuthProvider.ForceResendingToken
    private val firebaseAuth = Firebase.auth
    private var getCredential: PhoneAuthCredential? = null



    constructor(respondCodeInputActivity: SmsCodeCheckActivity?, phoneNumber: String) {
        this.smsCodeCheckActivity = respondCodeInputActivity
        this.phoneNumber = phoneNumber
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            smsCodeCheckActivity?.loadingProgressBar?.visibility = View.INVISIBLE

        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            showCorrectLoginDialogBox("Coś poszło nie tak")
            smsCodeCheckActivity?.loadingProgressBar?.visibility = View.INVISIBLE
            //respondCodeInputActivity?.button?.isClickable = false
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }


        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {// Save verification ID and resending token so we can use them later
            verificationID = verificationId
            respondToken = token;
            smsCodeCheckActivity?.loadingProgressBar?.visibility = View.INVISIBLE
            Log.i("VerificationID", verificationId)
            Log.i("Resend Token", token.toString())

        }
    }

    fun SendVeryficationCode() {
        smsCodeCheckActivity?.loadingProgressBar?.visibility = View.VISIBLE
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(smsCodeCheckActivity!!)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun signInWithPhoneAuthCredential() {
        try {
            getCredential = PhoneAuthProvider.getCredential(verificationID, smsCodeCheckActivity?.respondCodeTextView?.text.toString())
            firebaseAuth.signInWithCredential(getCredential!!)
                .addOnCompleteListener(smsCodeCheckActivity!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")

                        StartUpController.currentUser = firebaseAuth.currentUser
                        if (StartUpController.currentUser != null) {
                        StartUpController.loggedUser = StartUpController.getCurrentUser(
                            smsCodeCheckActivity!!
                        )
                        showCorrectLoginDialogBox("Weryfikacja poprawna")
                        smsCodeCheckActivity?.loadingProgressBar?.visibility =
                            View.INVISIBLE
                    }

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                showWrongLoginDialogBox("Weryfikacja niepoprawna")
                                smsCodeCheckActivity?.loadingProgressBar?.visibility =
                                    View.INVISIBLE
                            }
                        }
                    }

        } catch (e : Exception)  {
            showWrongLoginDialogBox("Weryfikacja niepoprawna")
        }

}

fun showCorrectLoginDialogBox(message: String) {
    val alertDialog: AlertDialog = AlertDialog.Builder(smsCodeCheckActivity).create()
    alertDialog.setTitle("Weryfikacja")
    alertDialog.setMessage(message)
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        { dialog, which -> dialog.dismiss()
            val intent = Intent(smsCodeCheckActivity, UserInformationActivity::class.java)
            smsCodeCheckActivity!!.startActivity(intent)
        smsCodeCheckActivity!!.finish()})
    alertDialog.show()
}

    fun showWrongLoginDialogBox(message: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(smsCodeCheckActivity).create()
        alertDialog.setTitle("Weryfikacja")
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            { dialog, which -> dialog.dismiss()})
        alertDialog.show()
    }


}





