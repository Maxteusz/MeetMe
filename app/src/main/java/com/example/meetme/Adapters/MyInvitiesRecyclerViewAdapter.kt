package com.example.meetme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Controllers.MyInvitationsFragmentController
import com.example.meetme.Fragments.SearchInvitationsFragment
import com.example.meetme.Models.Invited
import com.example.meetme.R

class MyInvitiesRecyclerViewAdapter(private val invitations: List<Invited>, val myInvitationsFragmentController: MyInvitationsFragmentController) :
    RecyclerView.Adapter<MyInvitiesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_my_invities_recycler_view_adapter, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = invitations[position]
        holder.title_textView.text = ItemsViewModel.title
        holder.deleteInvited_button.setOnClickListener {
            view -> myInvitationsFragmentController.DeleteInvitation(invitations[position].uid.toString())
        }
    }

    override fun getItemCount(): Int {
        return invitations.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_textView : TextView;
        val deleteInvited_button : Button;
        init {
            title_textView = itemView.findViewById(R.id.title_textView)
            deleteInvited_button = itemView.findViewById(R.id.delete_invited_button)
        }

    }
}