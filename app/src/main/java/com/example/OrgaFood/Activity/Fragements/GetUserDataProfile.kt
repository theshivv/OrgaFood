package com.example.OrgaFood.Activity.Fragements

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_get_user_data_profile.*
import kotlinx.android.synthetic.main.fragment_get_user_data_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_view.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//private val FS = FirebaseFirestore.getInstance()
var firstN  = "null"
var lastN = "null"
var emailAdd = " "
var phoneN = " "
var address = " "
lateinit var  filepath : Uri
//lateinit var  cr : ContentResolver


/**
 * A simple [Fragment] subclass.
 * Use the [GetUserDataProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetUserDataProfile : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


       val xmlData = inflater.inflate(R.layout.fragment_get_user_data_profile, container, false)

xmlData.ProfileImageView.setOnClickListener({
    startFileChooser()
    ProfileImageView.setImageResource(R.drawable.ic_baseline_edit_24)

})



//        Log.d(TAG, firstName.toString())
xmlData.saveChanges.setOnClickListener {
    Log.e(TAG, "onCreateView: save changes button is running")

    getUserData(xmlData)


//
}


        return xmlData
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GetUserDataProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GetUserDataProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    fun getUserData(view: View){


            firstN = firstName.text.toString()
         emailAdd = emailAddressOfUser.text.toString()
         phoneN = phoneNo.text.toString()
        address = addressOfUser.text.toString()

        val checkedId = view.radioGender.checkedRadioButtonId
        val radio: RadioButton = view.findViewById(checkedId)
        var gender =  radio.text.toString()


        Log.d(TAG, firstN + emailAdd + phoneN + address)

        val userInfo = User(
            FireStoreC().getCurrentUID(),
            firstN,
            lastN,
            emailAdd,
            phoneN.toLong(),
            address,
            gender,
            uploadImage()

        )

        FireStoreC().completeTheUserProfile(userInfo)
    }


    private fun startFileChooser(){
       var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "choose Picture"), 111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == Activity.RESULT_OK && data!= null){
           filepath = data.data!!
            val resolver = context?.applicationContext?.contentResolver

            var bitmap = MediaStore.Images.Media.getBitmap(resolver, filepath)
            ProfileImageView.setImageBitmap(bitmap)
        }
    }


    private fun uploadImage() : String {
        var imageUrlSt : String = "https://firebasestorage.googleapis.com/v0/b/orgafood-859fc.appspot.com/o/defaultProfilePic.png?alt=media&token=1948043e-2e8c-4137-85d9-dc4870528361"

        if (filepath != null) {
            val fileName = FireStoreC().getCurrentUID().toString() +".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(filepath)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                         imageUrlSt = it.toString()
                        Log.d(TAG, "uploadImage: url issss " + imageUrlSt,)
                    }
                }

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }


        return imageUrlSt
    }



}


