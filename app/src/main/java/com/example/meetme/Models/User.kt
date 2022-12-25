package com.example.meetme.Models


import android.content.Context
import com.example.meetme.Dialogs.Dialogs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class User : Serializable {
    var uid : String? = "";
    var nick : String? = "";
    var aboutMe : String? = "";
    var token : String? = ""
    @Exclude
    private var auth : FirebaseAuth? = null;

    constructor(uid: String?, nick: String?, aboutMe: String?, token : String?) {
        this.uid = uid
        this.nick = nick
        this.aboutMe = aboutMe
        this.token = token
    }

    constructor(auth : FirebaseAuth)
    {
        this.auth = auth
        this.uid = auth.currentUser?.uid.toString()
    }

    @Exclude
    fun isUserLogged() : Boolean
    {
        if(auth?.currentUser == null)
            return false;
        return true;
    }

    fun  ReadDataUser(successFunction: (it : QuerySnapshot) -> Unit, failFunction: () -> Unit, context : Context, userUid: String? = uid) {

        val dialog  = Dialogs.LoadingDialog(context);
        dialog.show("Ładowanie danych", {})

        var db = Firebase.firestore
        db.collection("Users")
            .whereEqualTo("uid", userUid)
            .get()
            .addOnSuccessListener {
                if(it.size() > 0) {
                    successFunction(it)
                     dialog.hide()
                }
            }
            .addOnFailureListener { failFunction()
                dialog.hide()
            }
    }

    enum class Sex
    {
        None,Male,Female
    }
    constructor()

}

