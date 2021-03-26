package com.example.OrgaFood.Activity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.Activity.Info.constants
import com.example.OrgaFood.R

class detailsActivity : AppCompatActivity() {

    private  var mproduct : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        if(intent.hasExtra(constants.EXTRA_PRODUCT_ID)){
            mproduct = intent.getStringExtra(constants.EXTRA_PRODUCT_ID)!!



            Log.i("Product id idkd", mproduct)
        }
getPodu()


    }

    private  fun getPodu(){
        FireStoreC().getProductsDetails(this,mproduct)
    }

    fun getAllProDetails(product:Product){
        Glide.with(this).load(product.images).into(findViewById(R.id.detailImg))
    }
}