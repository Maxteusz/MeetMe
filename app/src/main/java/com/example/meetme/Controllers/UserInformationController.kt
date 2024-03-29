package com.example.meetme.Controllers

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.UserInformationActivity
import com.example.meetme.Models.User
import com.example.meetme.Services.MyFirebaseMessagingService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UserInformationController(val userInformationActivity: UserInformationActivity) {

    var db = Firebase.firestore
    var refStorage = FirebaseStorage.getInstance().reference


    fun AddImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        userInformationActivity.startActivityForResult(gallery, 3)
    }

    fun SaveImage(image: ImageView?) {

        if (image != null) {
            val refStorage =
                FirebaseStorage.getInstance().reference.child(StartUpController.currentUser?.uid!!)
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val reduceBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true)
            val baos = ByteArrayOutputStream()
            reduceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            refStorage.putBytes(data)
        }


    }

    fun DownloadImage() =
        refStorage.child(StartUpController.currentUser?.uid!!).getBytes(1024 * 1024 * 5)

    fun SaveUserData() {
        val db = Firebase.firestore
        db.collection("Users")
            .add(CreateUser())
            .addOnSuccessListener {
                val intent = Intent(userInformationActivity, MenuActivity::class.java)
                userInformationActivity.startActivity(intent)
                userInformationActivity.finish()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun UpdateUserData(token: String) {
        val user =
            CreateUser()
        db.collection("Users").document(token)
            .update(
                mapOf(
                    "nick" to user.nick,
                    "aboutMe" to user.aboutMe,
                     "token" to MyFirebaseMessagingService.fcmToken
                )
            )
            .addOnSuccessListener {
                val intent = Intent(userInformationActivity, MenuActivity::class.java)
                userInformationActivity.startActivity(intent)
                userInformationActivity.finish()
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    private fun CreateUser(): User {

        val nick = userInformationActivity.nick_TextView?.text.toString()
        val aboutMe: String = userInformationActivity.aboutMe_TextView?.text.toString()
        val token = MyFirebaseMessagingService.fcmToken
        val uid = StartUpController.currentUser?.uid
        return User(uid!!, nick, aboutMe, token!!)

    }

   // fun test ()

    fun FillActivity(user: User) {

        userInformationActivity.nick_TextView?.text = user.nick
        userInformationActivity.aboutMe_TextView?.text = user.aboutMe

    }


}