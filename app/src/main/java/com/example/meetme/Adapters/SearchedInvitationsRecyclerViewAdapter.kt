package com.example.meetme.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Controllers.SearchedInvitationsFragmentController
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.Dialogs.Dialogs
import com.example.meetme.Interfaces.IDialog
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Request
import com.example.meetme.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class SearchedInvitationsRecyclerViewAdapter(
    private val invitations: Array<Invited>,
    val searchedInvitationsFragmentController: SearchedInvitationsFragmentController,
    val context: Context
) :
    RecyclerView.Adapter<SearchedInvitationsRecyclerViewAdapter.ViewHolder>() {

    val dialogs = mapOf<String, IDialog>(
        "LoadingDialog" to Dialogs.LoadingDialog(context),
        "InformationDialog" to Dialogs.InformationDialog(context),
        "YesNoDialog" to Dialogs.YesNoDialog(context)
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_searched_invited, parent, false)

        return ViewHolder(view)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Invitation = invitations[position]
        holder.title_textView.text = Invitation.title
        if (invitations[position].iHavePlace == true)
            holder.statusPlace_imageView.setImageResource(R.drawable.check_icon)
        else
            holder.statusPlace_imageView.setImageResource(R.drawable.unchecked_icon)

        holder.place_textView.text = Invitation.place
        searchedInvitationsFragmentController.downloadImage(Invitation.owner?.uid!!)
            .addOnCompleteListener {
                try {
                    val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result.size)
                    val reduceBitmap = Bitmap.createScaledBitmap(bmp, 200, 200, true)
                    holder.user_image.setImageBitmap(reduceBitmap)
                } catch (e: Exception) {
                }
            }
        holder.cardView.setOnClickListener {
            dialogs["YesNoDialog"]?.show("Czy chcesz dodać żądanie", {
                dialogs["LoadingDialog"]?.show("Wysyłanie zapytania", {})
                holder.cardView.isClickable = false
                existsRequest(Invitation,
                    {
                    val request = Request(StartUpController.currentUser!!, Invitation)
                        request.sendRequest(
                        {
                            dialogs["InformationDialog"]?.show("Utworzono żądanie", {})
                            dialogs["LoadingDialog"]?.hide()
                            holder.cardView.isClickable = true
                        },
                        {
                            // Failure operation
                            dialogs["LoadingDialog"]?.hide()
                            holder.cardView.isClickable = true
                        })
                },
                    {
                        holder.cardView.isClickable = true
                    })
            })
        }

    }


    fun existsRequest(invited: Invited, addRequest: () -> Unit, unblockUI : () -> Unit) {
        Log.i("Current user UID", StartUpController.currentUser?.uid.toString())
        val db = Firebase.firestore
        db.collection("Requests")
            .whereEqualTo(FieldPath.of("ownerRequest", "uid"),StartUpController.currentUser?.uid!!)

            .get()
            .addOnSuccessListener {
                val requests = it?.toObjects<Request>()
                for (doc in requests!!) {
                    if (doc.invited?.geohash ==  invited.geohash) {
                        Log.i("Znaleziono", doc.invited?.uid + " " +  invited.uid);
                        dialogs["LoadingDialog"]?.hide()
                        dialogs["InformationDialog"]?.show("Żądanie już istnieje", {})
                        unblockUI()
                        return@addOnSuccessListener
                    }
                }
                addRequest()
            }
    }


    override fun getItemCount(): Int {
        return invitations.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_textView: TextView;
        val statusPlace_imageView: ImageView;
        val place_textView: TextView;
        val user_image: ImageView
        val cardView: MaterialCardView

        init {
            title_textView = itemView.findViewById(R.id.title_textView)
            statusPlace_imageView = itemView.findViewById(R.id.status_place)
            place_textView = itemView.findViewById(R.id.place_textview)
            user_image = itemView.findViewById(R.id.user_image)
            cardView = itemView.findViewById(R.id.searched_invited_card)
        }


    }
}

