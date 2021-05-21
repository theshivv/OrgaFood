package com.example.OrgaFood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.OrgaFood.R
import kotlinx.android.synthetic.main.activity_dashboard_home_screen.*

class DashboardHomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_dashboard_home_screen)



    }

    fun onCartClick(view: View) {

        startActivity(Intent(this,CartActivity ::class.java))
    }
}