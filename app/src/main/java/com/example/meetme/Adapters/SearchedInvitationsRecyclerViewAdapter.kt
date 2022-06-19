package com.example.meetme.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Activities.DetailsInviteActivity
import com.example.meetme.Activities.MenuActivity
import com.example.meetme.Controllers.SearchedInvitationsFragmentController

import com.example.meetme.Models.Invited
import com.example.meetme.R
import com.google.android.material.card.MaterialCardView
import java.lang.Exception

class SearchedInvitationsRecyclerViewAdapter (private val invitations: List<Invited>, val searchedInvitationsFragmentController: SearchedInvitationsFragmentController, val context: Context) :
    RecyclerView.Adapter<SearchedInvitationsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_searched_invited, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = invitations[position]
        holder.title_textView.text = ItemsViewModel.title
        if(invitations[position].iHavePlace == true)
            holder.statusPlace_imageView.setImageResource(R.drawable.check_icon)
        else
            holder.statusPlace_imageView.setImageResource(R.drawable.unchecked_icon)

        holder.place_textView.text = ItemsViewModel.place
        searchedInvitationsFragmentController.DownloadImage(ItemsViewModel.owner?.uid!!).addOnCompleteListener {
            try {
                val bmp = BitmapFactory.decodeByteArray(it.result, 0, it.result.size)
                val reduceBitmap = Bitmap.createScaledBitmap(bmp, 200, 200, true)
                holder.user_image.setImageBitmap(reduceBitmap)
            }
            catch (e : Exception) {}
        }
        holder.cardView.setOnClickListener {
            val intent = Intent(context, DetailsInviteActivity::class.java)
            context.startActivity(intent)
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

