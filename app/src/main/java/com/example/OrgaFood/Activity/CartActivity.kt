package com.example.OrgaFood.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.cart
import com.example.OrgaFood.Activity.model.cartAdapter
import com.example.OrgaFood.R
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


    }


    fun getCartItem(cartList :ArrayList<cart>)    // getting the cart list we call this function in firebase class and pass the list items
                                                   // here we will get the cart items and then we have to show them
    {
//        for(j in cartList){
//            Log.i("Cart Item t" ,j.sellerName)
//        }
        Log.i("Cart Item t" , (cartList.size).toString())
        if (cartList.size>0)
        {
            cartRcv.visibility = View.VISIBLE
            emptyCartImg.visibility  = View.GONE // as there are items in the cart we can disable the image of empty activity  and enable the recycle view
            emptyCartText.visibility = View.GONE
//            NumberOfItems.text = num.toString()
            cartRcv.layoutManager = LinearLayoutManager(this)
            cartRcv.adapter = cartAdapter(this,cartList)
            cartRcv.setHasFixedSize(true)

        }

    }
    override fun onResume(){
        super.onResume()
        addIemsTolist()
    }



    //This function will call FireStoreC().cartList(this) which is going to add items to of fireStore to list for rendering into recycle view
    private  fun  addIemsTolist(){
        FireStoreC().cartList(this)
    }
}