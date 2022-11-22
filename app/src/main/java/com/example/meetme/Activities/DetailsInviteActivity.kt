package com.example.meetme.Activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetme.Adapters.MessagesRecyclerViewAdapter
import com.example.meetme.Adapters.MyInvitiesRecyclerViewAdapter
import com.example.meetme.Adapters.RequestsRecyclerViewAdapter
import com.example.meetme.Controllers.DetailsInvitedController
import com.example.meetme.Controllers.RequestFragmentController
import com.example.meetme.Controllers.StartUpController
import com.example.meetme.Fragments.MyInvitedFragment
import com.example.meetme.Models.Invited
import com.example.meetme.Models.Message
import com.example.meetme.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DetailsInviteActivity : AppCompatActivity() {
   lateinit var  tMessage : TextInputLayout
   lateinit var eMessage: TextInputEditText
   lateinit var tTitle : TextView

    var detailsInvitedController : DetailsInvitedController? = null
    var recyclerView : RecyclerView? = null;
    var layoutManager : LinearLayoutManager? = null
    var adapter : MessagesRecyclerViewAdapter? = null;




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_invite)
        tMessage = findViewById(R.id.message_textinputlayout)
        eMessage = findViewById(R.id.message_edittext)
        tTitle = findViewById(R.id.title_textView)
        var bundle :Bundle ?=intent.extras
        val invited = bundle?.getSerializable("EXTRA_INVITATION") as Invited?
        tTitle.text = invited?.title

        tMessage.setEndIconOnClickListener(){
            val text = eMessage.text
            if(!text.isNullOrEmpty()) {
                val message = Message(StartUpController.currentUser!!.nick!!, text.toString())
                message.sendMessage(invited!!)
                eMessage.text = null
            }
        }
        detailsInvitedController = DetailsInvitedController(this)
        val message = detailsInvitedController!!.getMessages(invited!!)
        adapter = MessagesRecyclerViewAdapter(message)
        recyclerView = findViewById(R.id.messages_recyclerview)
        layoutManager = LinearLayoutManager(this.applicationContext)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

        adapter?.notifyDataSetChanged()
    }
}