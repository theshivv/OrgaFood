package com.example.OrgaFood.Activity.FireStore

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class snakeBar {
    fun onSNACK(view: View, messageDisplauOn : String){
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, messageDisplauOn,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#61bced"))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.textSize = 10f
        snackbar.show()
    }
}