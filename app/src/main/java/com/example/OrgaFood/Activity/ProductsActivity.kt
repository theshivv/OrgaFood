package com.example.OrgaFood.Activity

import android.content.Intent
import android.media.Image
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.Product

import com.example.OrgaFood.Activity.model.dataAdapter
import com.example.OrgaFood.R
import java.util.ArrayList

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)



//

        FireStoreC().getProductsinfo(this)



//        Log.d(ContentValues.TAG, "${"the size is"} => ${FireStoreC().productsList.size}"
//        rcv.adapter= dataAdapter(this,productsList)
//        rcv.setHasFixedSize(true)
//        Log.d(ContentValues.TAG, "${"ThisProducts page"+ plis.size} => ${plis.size}")


        var profile= findViewById<View>(R.id.profilep)
        profile.setOnClickListener{
            startActivity(Intent(this,ProfileActivity ::class.java))
        }

        var img= findViewById<View>(R.id.cartImgId)
        img.setOnClickListener{
            startActivity(Intent(this,CartActivity ::class.java))
        }


    }



    fun successProduc(plist: ArrayList<Product>) {

        val rcv= findViewById<RecyclerView>(R.id.recycleView)
        rcv.adapter=dataAdapter(this,plist)
        rcv.setHasFixedSize(true)
    }



}




