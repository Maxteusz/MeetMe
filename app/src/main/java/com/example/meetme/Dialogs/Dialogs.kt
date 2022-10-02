package com.example.meetme.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import android.widget.LinearLayout
import android.widget.TextView
import com.example.meetme.R
import com.google.android.material.button.MaterialButton

class Dialogs {
    internal class LoadingDialog {
        companion object {
            private var dialog: Dialog? = null

            fun show(context : Context? , activity: Activity?, message: String) {
                if (context != null)
                    dialog = Dialog(context)
                else
                dialog = Dialog(activity!!)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setContentView(R.layout.layout_loading_dialog)
                var textView: TextView? = dialog!!.findViewById(R.id.loading_text)
                textView?.text = message

                dialog!!.show()
            }

            fun hide() {
                if (dialog != null) {
                    dialog!!.dismiss()
                }
            }
        }
    }

        internal class InfomationDialog {
            companion object {
                private var dialog: Dialog? = null
                fun show(context : Context?,activity: Activity?, message: String?, closeActivity : Boolean = false) {
                    if (context != null)
                        dialog = Dialog(context)
                    else
                        dialog = Dialog(activity!!)
                    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog!!.setContentView(R.layout.layout_information_dialog)
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    var button: MaterialButton = dialog!!.findViewById(R.id.ok_button)
                    var info : TextView = dialog!!.findViewById(R.id.info_text)
                    info.text = message
                    button.setOnClickListener({
                        dialog!!.dismiss()
                        if(closeActivity)
                            activity?.finish()
                       })

                    try {
                        dialog!!.show()
                    } catch (e: Exception) {
                    }
                }


            }
        }
    }





