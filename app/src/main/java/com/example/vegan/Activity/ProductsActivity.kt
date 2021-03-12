package com.example.vegan.Activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.vegan.Activity.FireStore.FireStoreC
import com.example.vegan.Activity.Info.Product

import com.example.vegan.Activity.model.dataAdapter
import com.example.vegan.R
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

    }



    fun successProduc(plist: ArrayList<Product>) {

        val rcv= findViewById<RecyclerView>(R.id.recycleView)
       rcv.adapter=dataAdapter(this,plist)
        rcv.setHasFixedSize(true)
    }

}