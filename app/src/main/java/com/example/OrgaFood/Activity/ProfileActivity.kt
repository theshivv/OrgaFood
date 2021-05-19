package com.example.OrgaFood.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    private val FS = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var fnameL ="fname"
        var lastname = "lastname"
        var emailide = "emaid"



///        Log.i("profile id", id)

                val docRef: DocumentReference = FS.collection("users").document(FireStoreC().getCurrentUID())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {


//                    Log.i("LOGGER", "First " + document.getString("fname"))
//                    Log.i("LOGGER", "LastRender " + document.getString("lname"))
//                    Log.i("LOGGER", "Born " + document.getString("email"))
                    fnameL = document.getString("fname").toString()
                    lastname = document.getString("lname") .toString()
                    emailide = document.getString("email").toString()
                    findViewById<TextView>(R.id.lUserName).text =fnameL
                    findViewById<TextView>(R.id.lUserEmailId).text = emailide
                    findViewById<TextView>(R.id.lUserLastName).text = lastname

                } else {
                    Log.d("LOGGER", "No such document")
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.exception)
            }
        }




    }

//    fun getInfoOfProfile(profileInfo: User) {
////        if(FireStoreC().getCurrentUID()!=null){
////            findViewById<TextView>(R.id.lUserName).text = FireStoreC().getCurrentUID()
////        }
////     var FirstName=  profileInfo.getString("fname")
////        var LastName = profileInfo.getString("lname")
////        var Email = profileInfo.getString("email")
//
////        findViewById<TextView>(R.id.lUserEmailId).text = profileInfo.email
//
////            Log.i("Product id idkdp", profileInfo.email)
//
//
//
//    }
}