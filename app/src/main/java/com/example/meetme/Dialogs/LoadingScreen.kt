package com.example.meetme.Dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.example.meetme.R

class LoadingScreen {
   private var dialog: Dialog? = null
    val message : String

    constructor(message: String) {
        this.message = message
    }




    fun displayLoading(context: Context?) {
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_loading_dialog)
        var textView : TextView? = dialog!!.findViewById<TextView>(R.id.loading_text)
        textView?.text = message
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}