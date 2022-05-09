package com.example.meetme.Services


import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService() : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("TAG", "From: " + message.getFrom());
        Log.d("TAG", "Notification Message Body: " + message.getNotification()?.getBody());
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        Log.i("Token Service", token)
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("FcmToken", token).apply();
        super.onNewToken(token)
    }

}