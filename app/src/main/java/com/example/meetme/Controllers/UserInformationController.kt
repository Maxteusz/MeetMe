package com.example.meetme.Controllers

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.delay
import java.io.ByteArrayOutputStream

class UserInformationController (val userInformationActivity: UserInformationActivity) {

    var db = Firebase.firestore
   var refStorage = FirebaseStorage.getInstance().reference



  fun  ReadDataUser()  = db.collection("Users")
                .whereEqualTo("uid", StartUpController.loggedUser?.uid)
                .get()

    fun AddImage()
    {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        userInformationActivity.startActivityForResult(gallery,3)
    }
    fun SaveImage (image : ImageView?) {

        if(image != null) {
            val refStorage =
                FirebaseStorage.getInstance().reference.child(StartUpController.loggedUser?.uid!!)
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            refStorage.putBytes(data)
        }


    }

    fun DownloadImage () = refStorage.child(StartUpController.loggedUser?.uid!!).getBytes(1024 * 1024 *5)

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

    fun UpdateUserData (token : String)
    {
        val user = CreateUser()
         db.collection("Users").document(token)
            .update(mapOf(
                "nick" to user.nick,
                "aboutMe" to user.aboutMe))
            .addOnSuccessListener {
                val intent = Intent(userInformationActivity, MenuActivity::class.java)
                userInformationActivity.startActivity(intent)
                userInformationActivity.finish()
                Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    private fun CreateUser () : User
    {

        val nick  = userInformationActivity.nick_TextView?.text.toString()
        val aboutMe : String = userInformationActivity.aboutMe_TextView?.text.toString()
        val token = MenuActivityController.getRegistrationToken(userInformationActivity)
        val uid = StartUpController.loggedUser?.uid
        return User(uid!!,nick,aboutMe,token)

    }

      fun FillActivity (user : User)
    {

            userInformationActivity.nick_TextView?.text = user.nick
            userInformationActivity.aboutMe_TextView?.text = user.aboutMe

    }





}