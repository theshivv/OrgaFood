package com.example.OrgaFood.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.Activity.Info.constants
import com.example.OrgaFood.R

class detailsActivity : AppCompatActivity() {

    private  var setProductId : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        if(intent.hasExtra(constants.EXTRA_PRODUCT_ID))
        {
            setProductId = intent.getStringExtra(constants.EXTRA_PRODUCT_ID)!!  //getting the product id



            Log.i("Product id idkd", setProductId)
        }

        getPodu()

    }

    private fun getPodu() {

        FireStoreC().getProductsDetails(this,setProductId)  //passing detail activity and product id to fireStoreC
    }
//
//
    fun getAllProDetails(product:Product) //call this function in FireStoreC and pass productDetails corresponding to the id that we pass in getProd() fun
    {



        Log.i("Product id idkd", product.pName)
   Glide.with(this).load(product.images).into(findViewById(R.id.dImage))
   findViewById<TextView>(R.id.DNameOfProduct).text = product.pName
   findViewById<TextView>(R.id.dPriceOfProduct).text = product.price
   findViewById<TextView>(R.id.dSellerName).text = product.sellerName

    }

}

