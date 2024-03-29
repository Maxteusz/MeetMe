package com.example.meetme.Adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Activities.DetailsInviteActivity
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Models.Invited
import com.example.meetme.R
import com.google.android.material.card.MaterialCardView

class MyInvitiesRecyclerViewAdapter(private val invitations: List<Invited>, val myInvitationsFragmentController: MyInvitationsFragmentController) :
    RecyclerView.Adapter<MyInvitiesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_my_invities_recycler_view_adapter, parent, false)

        return ViewHolder(view)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = invitations[position]
        holder.title_textView.text = ItemsViewModel.title
        holder.deleteInvited_button.setOnClickListener {
            view -> myInvitationsFragmentController.deleteInvitation(invitations[position].uid.toString())
        }
        if(invitations[position].iHavePlace == true)
            holder.statusPlace_imageView.setImageResource(R.drawable.check_icon)
        else
            holder.statusPlace_imageView.setImageResource(R.drawable.unchecked_icon)
            holder.place_textView.text = ItemsViewModel.place

        holder.main_card.setOnClickListener({
            val intent = Intent(myInvitationsFragmentController.myInvitationsFragment.context, DetailsInviteActivity::class.java)
            intent.putExtra("EXTRA_INVITATION", invitations[position])
            myInvitationsFragmentController.myInvitationsFragment.context?.startActivity(intent)
        })


    }

    override fun getItemCount(): Int {
        return invitations.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_textView : TextView;
        val deleteInvited_button : Button;
        val statusPlace_imageView : ImageView;
        val place_textView : TextView;
        val main_card : MaterialCardView


        init {
            title_textView = itemView.findViewById(R.id.title_textView)
            deleteInvited_button = itemView.findViewById(R.id.delete_invited_button)
            statusPlace_imageView = itemView.findViewById(R.id.status_place)
            place_textView = itemView.findViewById(R.id.place_textview)
            main_card = itemView.findViewById(R.id.main_card)
        }

    }
}