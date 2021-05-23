package com.example.OrgaFood.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.Activity.Info.cart
import com.example.OrgaFood.Activity.Info.constants
import com.example.OrgaFood.R

class detailsActivity : AppCompatActivity() {

    private  var setProductId : String =""
    private lateinit var producD : Product


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        if(intent.hasExtra(constants.EXTRA_PRODUCT_ID))
        {
            setProductId = intent.getStringExtra(constants.EXTRA_PRODUCT_ID)!!  //getting the product id

            Log.i("Product id idkd", setProductId)
        }

        getPodu()


// if add to cat button is pressed then addToC() function will run and this function will pass the data  to FireStoreC().cart()
        // cart() function will  add all those data to fireStore
        findViewById<Button>(R.id.addButton).setOnClickListener {
addToC()
        }

    }
//////////////////////////////////////////////

    private fun getPodu() {

        FireStoreC().getProductsDetails(this,setProductId)  //passing detail activity and product id to fireStoreC
    }
//
//
    fun getAllProDetails(product:Product) //call this function in FireStoreC and pass productDetails corresponding to the id that we pass in getProd() fun
    {

producD = product

        Log.i("Product id idkd", product.pName)
   Glide.with(this).load(product.images).into(findViewById(R.id.dImage))
   findViewById<TextView>(R.id.DNameOfProduct).text = product.pName
   findViewById<TextView>(R.id.dPriceOfProduct).text = product.price
   findViewById<TextView>(R.id.dSellerName).text = product.sellerName

    }




// function addToC() will  create the variable of the type cart and pass this variable(addToCart) to FireStoreC().cart()
    private fun addToC(){

    var setProductIdForCart = setProductId + FireStoreC().getCurrentUID()

        val addToCart = cart(
FireStoreC().getCurrentUID(),          // getting the id of current user
            producD.pName,
            producD.images,
            setProductId  ,
            producD.price ,
            producD.sellerName,
            constants.NO_OFItems,
            setProductIdForCart

        )
        FireStoreC().cart(this,addToCart,setProductId)
    }

    //we call this function if the data is added successfully to cart in FireStoreC().cart()
    fun addSucc(){
        Toast.makeText(this,"Add to cart",Toast.LENGTH_SHORT).show()
    }

}

