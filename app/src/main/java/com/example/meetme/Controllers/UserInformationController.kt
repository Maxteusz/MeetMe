package com.example.meetme.Controllers

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Activities.UserInformationActivity
import com.example.meetme.Models.Invited
import com.example.meetme.Models.LoadingScreen
import com.example.meetme.Models.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UserInformationController (val userInformationActivity: UserInformationActivity) {

    val database = FirebaseDatabase.getInstance()

    fun AddImage()
    {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        userInformationActivity.startActivityForResult(gallery,3)
    }
    fun SaveData (image : ImageView?)
    {
        if(image != null) {
            val loadingScreen = com.example.meetme.Dialogs.LoadingScreen("Zapisywanie")
            loadingScreen.displayLoading(userInformationActivity)
            val refStorage =
                FirebaseStorage.getInstance().reference.child(StartUpController.loggedUser.uid)
            image?.isDrawingCacheEnabled = true
            image?.buildDrawingCache()
            val bitmap = (image?.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = refStorage.putBytes(data)
            uploadTask.addOnFailureListener {
                loadingScreen.hideLoading()
            }.addOnSuccessListener { taskSnapshot ->
                SaveUser(loadingScreen)


            }
        }
    }

    private fun SaveUser(loadingScreen: com.example.meetme.Dialogs.LoadingScreen) {
        loadingScreen.displayLoading(userInformationActivity)
        val db = Firebase.firestore
        db.collection("Users")
            .add(CreateUser())
            .addOnSuccessListener { documentReference ->
                loadingScreen.hideLoading()
                val intent = Intent(userInformationActivity, MenuActivity::class.java)
                userInformationActivity!!.startActivity(intent)
                userInformationActivity!!.finish()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
                loadingScreen.hideLoading()

            }

    }

    private fun CreateUser () : User
    {
        val nick  = userInformationActivity.nick_TextView?.text.toString()
        val aboutMe : String = userInformationActivity.aboutMe_TextView?.text.toString()
        val token = MenuActivityController.getRegistrationToken(userInformationActivity)
        val uid = StartUpController.loggedUser.uid

        return User(uid,nick,aboutMe,token)

    }





}