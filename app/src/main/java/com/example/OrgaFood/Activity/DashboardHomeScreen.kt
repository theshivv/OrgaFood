package com.example.OrgaFood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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



    //Navigation Bar

    fun OnProductClick(item: MenuItem) {
        startActivity(Intent(this,ProductsActivity ::class.java))
        Toast.makeText(this,"Product",Toast.LENGTH_SHORT).show()

    }

    fun OnProfileClick(item: MenuItem) {
        startActivity(Intent(this, ProfileActivity::class.java))
        Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show()
    }

    fun OnProductClick(view: View) {
        startActivity(Intent(this,ProductsActivity ::class.java))
    }

    fun UploadProduct(view: View) {
        startActivity(Intent(this,UploadProductActivity ::class.java))
    }


}