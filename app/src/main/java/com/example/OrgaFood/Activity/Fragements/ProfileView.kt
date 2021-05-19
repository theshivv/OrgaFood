package com.example.OrgaFood.Activity.Fragements

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

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
                    uiLayout.findViewById<TextView>(R.id.lUserName).text =fnameL
                    uiLayout.findViewById<TextView>(R.id.lUserEmailId).text = emailide
                    uiLayout.findViewById<TextView>(R.id.lUserLastName).text = lastname

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