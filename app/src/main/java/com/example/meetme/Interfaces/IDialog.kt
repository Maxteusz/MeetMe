package com.example.meetme.Interfaces

import android.content.Context

interface IDialog {

    val context : Context


    fun show (text : String)
    fun hide ()
}