package com.example.meetme.Controllers

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.util.Log
import android.view.View
import com.example.meetme.Activities.SmsCodeCheckActivity
import com.example.meetme.Models.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.concurrent.TimeUnit


class RegistartionController {

    var respondCodeInputActivity: SmsCodeCheckActivity? = null
    var phoneNumber: String = "";
    private lateinit var verificationID: String
    private lateinit var respondToken: PhoneAuthProvider.ForceResendingToken
    private val firebaseAuth = Firebase.auth
    var user: User? = null
    private lateinit var getCredential: PhoneAuthCredential


    constructor(respondCodeInputActivity: SmsCodeCheckActivity?, phoneNumber: String) {
        this.respondCodeInputActivity = respondCodeInputActivity
        this.phoneNumber = phoneNumber
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            getCredential = credential
            respondCodeInputActivity?.respondCodeTextView?.setText(credential.smsCode)
            respondCodeInputActivity?.loadingProgressBar?.visibility = View.INVISIBLE

        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            showDialogBox("Coś poszło nie tak")
            respondCodeInputActivity?.loadingProgressBar?.visibility = View.INVISIBLE
            //respondCodeInputActivity?.button?.isClickable = false
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            verificationID = verificationId
            respondToken = token;
            respondCodeInputActivity?.loadingProgressBar?.visibility = View.INVISIBLE
            Log.i("VerificationID", verificationId)
            Log.i("Resend Token", token.toString())

        }
    }

    fun SendVeryficationCode() {
        respondCodeInputActivity?.loadingProgressBar?.visibility = View.VISIBLE
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(respondCodeInputActivity!!)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun signInWithPhoneAuthCredential() {
        try {
            firebaseAuth.signInWithCredential(this.getCredential)
                .addOnCompleteListener(respondCodeInputActivity!!) { task ->
                    if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            user =
                                User(
                                    task.result?.user!!.uid,
                                    "Maxteusz",
                                    User.Sex.Male,
                                    "Jestem Super"
                                )
                            if (user != null) {
                                showDialogBox("Weryfikacja poprawna")
                                respondCodeInputActivity?.loadingProgressBar?.visibility =
                                    View.INVISIBLE
                            }
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                showDialogBox("Weryfikacja niepoprawna")
                                respondCodeInputActivity?.loadingProgressBar?.visibility =
                                    View.INVISIBLE
                            }
                        }
                    }

        } catch (e : Exception)  {
            showDialogBox("Weryfikacja niepoprawna")
        }

}

fun showDialogBox(message: String) {
    val alertDialog: AlertDialog = AlertDialog.Builder(respondCodeInputActivity).create()
    alertDialog.setTitle("Weryfikacja")
    alertDialog.setMessage(message)
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
    alertDialog.show()
}

}





