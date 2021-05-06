package com.example.OrgaFood.Activity.FireStore

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.example.OrgaFood.Activity.*
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.Activity.Info.cart
import com.example.OrgaFood.Activity.Info.constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
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
        if(currentU!=null){
            currentUid = currentU.uid
            Log.d("GotCurrentUid", currentUid)
            return  currentUid
        }
        else {


            return "Not Login"

        }

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
        FS.collection("cart").
        document(addedPid).set(addToCart, SetOptions.merge())   // adding the documents and if already present then merge
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

//    fun getUserInfo(activity :ProfileActivity ){
//        val docRef = FS.collection("users").document(getCurrentUID())
//
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    var id = getCurrentUID()
//
//                    Log.i(TAG,"ProfileIdIs",)
//                    Log.d(TAG, "DocumentSnapshot dataProfile: ${document.data}")
//
//                    Log.i("LOGGER","First "+document.getString("fName"));
//                    Log.i("LOGGER","LastNameRender "+document.getString("lname"));
//                    val pdata = document.toObject(User::class.java)
//                    if (pdata != null) {
//                        Log.d(TAG, "DocumentSnapshot dataProfileFname: ${pdata.fname}")
//                        activity.getInfoOfProfile(pdata)
//                    }
//                    else{
//                        Log.d(TAG,"dataNOtFound")
//                    }
//
//                } else {
//                    Log.d(TAG, "No such documentFound")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed withError ", exception)
//            }


//        val docRef: DocumentReference = FS.collection("users").document(getCurrentUID())
//        docRef.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val document = task.result
//                if (document != null) {
//                    Log.i("LOGGER", "First " + document.getString("fname"))
//                    Log.i("LOGGER", "LastRender " + document.getString("lname"))
//                    Log.i("LOGGER", "Born " + document.getString("email"))
//                    activity.getInfoOfProfile(document)
//                } else {
//                    Log.d("LOGGER", "No such document")
//                }
//            } else {
//                Log.d("LOGGER", "get failed with ", task.exception)
//            }
//        }
//    }





}
