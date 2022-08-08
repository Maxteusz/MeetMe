package com.example.meetme.Adapters

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Controllers.SearchedInvitationsFragmentController
import com.example.meetme.Controllers.StartUpController

import com.example.meetme.Models.Invited
import com.example.meetme.Models.LoadingScreen
import com.example.meetme.Models.Request
import com.example.meetme.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class SearchedInvitationsRecyclerViewAdapter (private val invitations: List<Invited>, val searchedInvitationsFragmentController: SearchedInvitationsFragmentController, val context: Context) :
    RecyclerView.Adapter<SearchedInvitationsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_searched_invited, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Invitation = invitations[position]
        holder.title_textView.text = Invitation.title
        if(invitations[position].iHavePlace == true)
            holder.statusPlace_imageView.setImageResource(R.drawable.check_icon)
        else
            holder.statusPlace_imageView.setImageResource(R.drawable.unchecked_icon)

        holder.place_textView.text = Invitation.place
        searchedInvitationsFragmentController.downloadImage(Invitation.owner?.uid!!).addOnCompleteListener {
            try {
                val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result.size)
                val reduceBitmap = Bitmap.createScaledBitmap(bmp, 200, 200, true)
                holder.user_image.setImageBitmap(reduceBitmap)
            }
            catch (e : Exception) {}
        }
        holder.cardView.setOnClickListener {
            existsRequest(Invitation, {
                val loadingScreen = LoadingScreen();
               // loadingScreen.displayLoading(context)
              /*  Request.sendRequest(StartUpController.currentUser!!.uid!!,Invitation.uid!!, Invitation.owner!!.uid,
                    {
                        //Success operation
                        loadingScreen.hideLoading()
                    },
                    {
                        // Failure operation
                        loadingScreen.hideLoading()
                        Toast.makeText(context,"Wystąpił błąd", Toast.LENGTH_SHORT)
                    })*/
            })



        }
    }



    fun existsRequest (invite: Invited, addRequest: () -> Unit )
    {

        val db = Firebase.firestore
        db.collection("Requests")
            .whereEqualTo("invitedID", invite.uid)
            .get()
            .addOnSuccessListener { result ->
                if (result.size() == 0)
                    addRequest()
                else
                    Toast.makeText(context, "Istnieje juz prośba o dodanie", Toast.LENGTH_LONG)


            }
            .addOnFailureListener { exception ->

            }
    }


    override fun getItemCount(): Int {
        return invitations.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title_textView : TextView;
        val statusPlace_imageView : ImageView;
        val place_textView : TextView;
        val user_image : ImageView
        val cardView : MaterialCardView

        init {
            title_textView = itemView.findViewById(R.id.title_textView)
            statusPlace_imageView = itemView.findViewById(R.id.status_place)
            place_textView = itemView.findViewById(R.id.place_textview)
            user_image = itemView.findViewById(R.id.user_image)
            cardView = itemView.findViewById(R.id.searched_invited_card)
        }



    }
}

