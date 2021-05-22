package com.example.OrgaFood.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Fragements.GetUserDataProfile
import com.example.OrgaFood.Activity.Fragements.ProfileView
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


            // loading the fragment
         var editProfileFragment = GetUserDataProfile()   //consturctor
         var getProfileFragment = ProfileView()      // Profile Constructor
         var editProfileIcon =  findViewById<View>(R.id.profileEdit)
         var profileText = findViewById<TextView>(R.id.profileH)



            supportFragmentManager.beginTransaction().replace(R.id.editFragment, getProfileFragment)
            .commit()

//            supportFragmentManager.beginTransaction().replace(R.id.editFragment, getProfileFragment)


            editProfileIcon.setOnClickListener {
                supportFragmentManager.beginTransaction().replace(R.id.editFragment, editProfileFragment).commit()
            }

            profileText.setOnClickListener {
                supportFragmentManager.beginTransaction().replace(R.id.editFragment, getProfileFragment)
                    .commit()
            }
        }

        }


