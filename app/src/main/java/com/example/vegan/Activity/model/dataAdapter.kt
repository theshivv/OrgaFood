package com.example.vegan.Activity.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vegan.Activity.Info.Product
import com.example.vegan.R
import java.util.*


class dataAdapter(private val context: Context,
                  private val list: ArrayList<Product>
                  ): RecyclerView.Adapter<dataAdapter.dataViewHolder>() {

class dataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   val imageVie:ImageView= itemView.findViewById(R.id.item_image)
    val textView:TextView=itemView.findViewById(R.id.item_title)
    val price: TextView = itemView.findViewById(R.id.price)
    val sellerName : TextView = itemView.findViewById(R.id.seller_name)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dataViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.productlayout,parent,false)
        return dataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: dataViewHolder, position: Int) {
val currentItem =list[position]


        Glide.with(context).load(currentItem.images).into(holder.imageVie)

        holder.textView.text= currentItem.pName
        holder.price.text= currentItem.price
        holder.sellerName.text= currentItem.sellerName

    }

    override fun getItemCount()= list.size
}








//        holder.imageView.setImageResource(currentItem.i)
//        val imageView: ImageView = findViewById(R.id.imageView)
//        Glide.with(this)
//            .load("https://www.tutorialspoint.com/images/tp-logo-diamond.png")
//            .into(imageView)