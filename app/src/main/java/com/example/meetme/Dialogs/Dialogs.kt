package com.example.meetme.Dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.meetme.Interfaces.IDialog
import com.example.meetme.R
import com.google.android.material.button.MaterialButton

class Dialogs {


    internal class LoadingDialog(override val context: Context) :
        IDialog {
        private var dialog: Dialog? = null
        override fun show(text: String, action: () -> Unit?) {


                dialog = Dialog(context)
            dialog!!.setCancelable(false)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setContentView(R.layout.layout_loading_dialog)
            var textView: TextView? = dialog!!.findViewById(R.id.info_text)
            textView?.text = text
            dialog!!.show()
        }



        override fun hide() {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        }
    }


    internal class InformationDialog(override val context: Context) :
        IDialog {
        private var dialog: Dialog? = null

        override fun show(text: String, action: () -> Unit?) {


                dialog = Dialog(context)
            dialog!!.setCancelable(false)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setContentView(R.layout.layout_information_dialog)
            var textView: TextView? = dialog!!.findViewById(R.id.info_text)
            var button: Button = dialog!!.findViewById(R.id.ok_button)
            button.setOnClickListener {
                dialog!!.dismiss()
                action()
            }
            textView?.text = text
            dialog!!.show()
        }


        override fun hide() {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        }
    }

    internal class YesNoDialog(override val context: Context) :
        IDialog {
        private var dialog: Dialog? = null


        override fun show(text: String, action: () -> Unit?) {
            dialog = Dialog(context)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setContentView(R.layout.layout_yes_no_dialog)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var noButton: MaterialButton = dialog!!.findViewById(R.id.no_button)
            var yesButton: MaterialButton = dialog!!.findViewById(R.id.yes_button)
            var info: TextView = dialog!!.findViewById(R.id.info_text)
            info.text = text
            noButton.setOnClickListener({
                dialog!!.dismiss()
            })
            yesButton.setOnClickListener {
                dialog!!.dismiss()
                action()
            }

            try {
                dialog!!.show()
            } catch (e: Exception) {
            }
        }

        override fun hide() {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        }
    }
}













