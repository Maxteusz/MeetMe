package com.example.meetme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Models.Request
import com.example.meetme.R

class RequestsRecyclerViewAdapter (private val requests: List<Request>):  RecyclerView.Adapter<RequestsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_request, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Request = requests[position]
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_textView : TextView;
        val deleteInvited_button : Button;
        val statusPlace_imageView : ImageView;
        val place_textView : TextView;
        init {
            title_textView = itemView.findViewById(R.id.title_textView)
            deleteInvited_button = itemView.findViewById(R.id.delete_invited_button)
            statusPlace_imageView = itemView.findViewById(R.id.status_place)
            place_textView = itemView.findViewById(R.id.place_textview)
        }

    }


}