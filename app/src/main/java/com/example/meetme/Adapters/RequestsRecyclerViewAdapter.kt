package com.example.meetme.Adapters

import android.util.Log
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
        val request = requests[position]
        holder.title_textView.text = request.invited?.title
        holder.describe.text = request.invited?.describe;
        holder.ownerNick.text = request.invited?.owner?.nick
    }

    override fun getItemCount(): Int {

        return requests.size
    }

    fun addMember (request: Request)
    {

    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val title_textView: TextView;
        //private val deleteInvited_button: Button;
        //private val statusPlace_imageView: ImageView;
        //private val place_textView: TextView;
        val describe : TextView
        val ownerNick : TextView;

        init {
            title_textView = itemView.findViewById(R.id.title_textView)
            //deleteInvited_button = itemView.findViewById(R.id.delete_invited_button)
          //  statusPlace_imageView = itemView.findViewById(R.id.status_place)
            //place_textView = itemView.findViewById(R.id.place_textview)
            describe = itemView.findViewById(R.id.describe_textfield)
            ownerNick = itemView.findViewById(R.id.nickName_textfield)
        }

    }


}