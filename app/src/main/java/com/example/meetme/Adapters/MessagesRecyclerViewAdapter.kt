package com.example.meetme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Models.Message
import com.example.meetme.R
import org.w3c.dom.Text

class MessagesRecyclerViewAdapter (private val messages: List<Message>) :  RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
       holder.tMessage.text = message.message
        holder.tUser.text = message.nickUser
    }

    override fun getItemCount(): Int {
       return messages.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tMessage : TextView
        val tUser : TextView

        init {
            tMessage = itemView.findViewById(R.id.message_textview)
            tUser = itemView.findViewById(R.id.user_textview)
        }

    }


}





