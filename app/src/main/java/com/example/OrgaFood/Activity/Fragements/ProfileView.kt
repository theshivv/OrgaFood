package com.example.OrgaFood.Activity.Fragements

import android.icu.util.ULocale.getName
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_get_user_data_profile.*
import kotlinx.android.synthetic.main.fragment_profile_view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val FS = FirebaseFirestore.getInstance()
/**
 * A simple [Fragment] subclass.
 * Use the [ProfileView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val uiLayout =inflater.inflate(R.layout.fragment_profile_view, container, false)
        var fnameL ="fname"
        var idOfUser = "id"
        var emailide = "emaid"
        var addressU = "add"
        var phoneNo = "phoneN"
        var gender  = "mfo"
        var dateOfBirth ="dob"
        var  imagelink = "link"



///        Log.i("profile id", id)

        val docRef: DocumentReference = FS.collection("users").document(FireStoreC().getCurrentUID())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {

                    fnameL = document.getString("fname").toString()
                    idOfUser = document.getString("id") .toString()
                    emailide = document.getString("email").toString()
                    addressU = document.getString("address").toString()
                    phoneNo = document.getString("pno").toString()
                    gender = document.getString("gender").toString()
                    imagelink = document.getString("imageLink").toString()
                    dateOfBirth = document.getString("dob").toString()


                    lUserName.text =fnameL
                    uiLayout.findViewById<TextView>(R.id.lUserEmailId).text = emailide
                    Glide.with(this).load(imagelink).into(profileimageView)
                    dateOfBirthView.text = dateOfBirth
                    viewPhoneNo.text = phoneNo
                    viewGender.text = gender
                    viewAddress.text = addressU
               GetUserDataProfile().getName(fnameL)




                } else {
                    Log.d("LOGGER", "No such document")
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.exception)
            }
        }
        return uiLayout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                ProfileView().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}