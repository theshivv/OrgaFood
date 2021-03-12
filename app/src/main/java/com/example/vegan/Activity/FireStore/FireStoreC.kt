package com.example.vegan.Activity.FireStore

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.example.vegan.Activity.Info.Product
import com.example.vegan.Activity.Info.User
import com.example.vegan.Activity.Info.constants
import com.example.vegan.Activity.ProductsActivity

import com.example.vegan.Activity.RegisterActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreC {


    private val FS = FirebaseFirestore.getInstance()
    val db = Firebase.firestore





    fun registerUser(activity: RegisterActivity,userInfo: User){

         FS.collection(constants.USERS).
         document(userInfo.id)
             .set(userInfo, SetOptions.merge()).addOnSuccessListener {
             Log.e(activity.javaClass.simpleName,"Data store in firebase")
         }
             .addOnFailureListener { e->
                 Log.e(
activity.javaClass.simpleName,"error",e)
             }
    }

    fun getCurrentUID(): String{
        val currentU = FirebaseAuth.getInstance().currentUser

        var currentUid=""
        if(currentUid!=null){
            currentUid = currentU.uid
        }
        return  currentUid
    }

fun getProductsinfo(activity: Activity){



   FS.collection("products")
       .get()
        .addOnSuccessListener { documents ->

            val productsList :ArrayList<Product> = ArrayList()


            for (document in documents) {

                Log.d(TAG, "${"documentID"+document.id} => ${"documentData"+document.data}")
                val produts = document.toObject(Product :: class.java)
                produts!!.ProDid = document.id

                productsList.add(produts)
                Log.d(TAG, "${"the size is"} => ${productsList.size}")
            }

            when(activity){
                is ProductsActivity ->{
                    activity.successProduc(productsList)
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }


}




}
