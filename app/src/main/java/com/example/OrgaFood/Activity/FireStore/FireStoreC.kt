package com.example.OrgaFood.Activity.FireStore

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.example.OrgaFood.Activity.CartActivity
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.Activity.Info.cart
import com.example.OrgaFood.Activity.Info.constants
import com.example.OrgaFood.Activity.ProductsActivity

import com.example.OrgaFood.Activity.detailsActivity
import com.example.OrgaFood.Activity.RegisterActivity2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreC {


    private val FS = FirebaseFirestore.getInstance()
    val db = Firebase.firestore


    fun registerUser(activity: RegisterActivity2, userInfo: User){

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

    fun completeTheUserProfile(userdata: User){

        FS.collection(constants.USERS).
        document(userdata.id)
            .set(userdata, SetOptions.merge()).addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun getCurrentUID(): String{
        val currentU = FirebaseAuth.getInstance().currentUser

        var currentUid=""
        if(currentU!=null){
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


    // getting product details for detail activity
    fun getProductsDetails(activity: detailsActivity, productId: String){

        FS.collection((constants.PROD))
            .document(productId) //getting the information of the product whose id is  pass from the activity
            .get()
            .addOnSuccessListener { document->
                Log.e(activity.javaClass.simpleName, document.toString())

                val product = document.toObject(Product::class.java)
                if(product!=null){
                    Log.d("Got the data",product.pName)
                    activity.getAllProDetails(product)

                }
                else{
                    Log.d("Empty doc",product.toString())

                }

            }
    }



    //creating collection cart adding document with name as product's document
    fun cart(activity: detailsActivity,addToCart :cart,addedPid:String)
    {
        var productidBasedOnCustomer = addedPid + getCurrentUID()
        FS.collection("cart").
        document(productidBasedOnCustomer).set(addToCart, SetOptions.merge())   // adding the documents and if already present then merge
            .addOnSuccessListener {
                activity.addSucc() // addSucc is just a toast message this function is created in details activity
            }
    }


    //adding the cart item data into list fro using it in the recycle view
    fun cartList(activity: Activity){


        FS.collection("cart")
            .whereEqualTo("userId",getCurrentUID()) //getting the cart item for particular user
            .get()
            .addOnSuccessListener {dataGet->
                val cartArrayList : ArrayList<cart> = ArrayList() //creating  the list
                for(data in  dataGet.documents)
                {
                    val cartItem = data.toObject(cart::class.java)!!
                    cartItem.ProDid = data.id
                    cartArrayList.add(cartItem)
                }

                when(activity){
                    is CartActivity ->{
                        activity.getCartItem(cartArrayList)
                    }
                }
            }
    }

    fun uploadPoduct(userdata: Product, nameDocument : String){

        FS.collection("products").
        document(nameDocument)
            .set(userdata, SetOptions.merge()).addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }


}
