package com.example.meetme.Adapters

import android.text.Layout.Alignment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.Models.Message
import com.example.meetme.R


class MessagesRecyclerViewAdapter (private val messages: List<Message>) :  RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false);
        val message = messages[position]
        holder.tMessage.text = message.message
        holder.tUser.text = message.nickUser
        if (message.nickUser == StartUpController.currentUser?.nick) {
            holder.lLayout.gravity = Gravity.END
            holder.tUser.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        } else {
            holder.lLayout.gravity = Gravity.START
            holder.tUser.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

        }



    }

    override fun getItemCount(): Int {
       return messages.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tMessage : TextView
        val tUser : TextView
        val lLayout : LinearLayout

        init {
            tMessage = itemView.findViewById(R.id.message_textview)
            tUser = itemView.findViewById(R.id.user_textview)
            lLayout = itemView.findViewById(R.id.main_LinearLayout)
        }

    }


}





