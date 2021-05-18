package com.example.OrgaFood.Activity.model

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.Info.cart
import com.example.OrgaFood.Activity.Info.constants
import com.example.OrgaFood.R
import kotlinx.android.synthetic.main.cart_layout.view.*

class cartAdapter(
    private val context : Context,
    private val  cList : ArrayList<cart>
): RecyclerView.Adapter<cartAdapter.cartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartViewHolder {
        var cartV  =  LayoutInflater.from(parent.context).inflate(R.layout.cart_layout,parent,false)

        return cartViewHolder(cartV)

    }

    override fun onBindViewHolder(holder: cartViewHolder, position: Int) {

        val cartI =cList[position]
        Glide.with(context).load(cartI.images).into(holder.itemView.cProductImg)


        holder.itemView.cProductName.text= cartI.pName
        holder.itemView.cPrice.text= cartI.price
        holder.itemView.cSellerName.text= cartI.sellerName
        holder.itemView.NumberOfItems.text=cartI.numOfItems
        Log.i("adapter cart",cartI.pName)

        holder.itemView.buyBtn.setOnClickListener {
            val intent = Intent()
            intent.setComponent(
                ComponentName( "com.razorpay.newsampleapp", "com.razorpay.sampleapp.kotlin.PaymentActivity")
            )
            intent.putExtra(constants.EXTRA_PRODUCT_ID, cartI.ProDid) //passing the selected product id and it's details to details activity

            context.startActivity((intent))
        }

    }
    override fun getItemCount(): Int {

        return cList.size
    }

    inner class cartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)   //passing the view in var itemView in Recycle view holder


}
