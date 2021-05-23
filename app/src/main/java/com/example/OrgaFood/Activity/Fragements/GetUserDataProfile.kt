package com.example.OrgaFood.Activity.Fragements

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_get_user_data_profile.*
import kotlinx.android.synthetic.main.fragment_get_user_data_profile.view.*


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
var imageurl =" "
var dateOfBirth = " "
var gender =" "
var idOfuse =" "
var nameOfperson = "Name"
lateinit var  filepath : Uri


//lateinit var  cr : ContentResolver
var imageUrlSt : String = "https://firebasestorage.googleapis.com/v0/b/orgafood-859fc.appspot.com/o/defaultProfilePic.png?alt=media&token=1948043e-2e8c-4137-85d9-dc4870528361"

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

       /////Default Initialization when Image is not selected ///////
        val builder = Uri.Builder()
        builder.scheme("https")
            .authority("www.myawesomesite.com")
            .appendPath("turtles")
            .appendPath("types")
            .appendQueryParameter("type", "1")
            .appendQueryParameter("sort", "relevance")
            .fragment("section-name")
        filepath = builder.build()
/////////////////////////////////////////////////////////////////////////////




       val xmlData = inflater.inflate(R.layout.fragment_get_user_data_profile, container, false)

        xmlData.findViewById<TextView>(R.id.showId).text = FireStoreC().getCurrentUID()

xmlData.ProfileImageView.setOnClickListener({
    startFileChooser()
    ProfileImageView.setImageResource(R.drawable.ic_baseline_edit_24)

})



//        Log.d(TAG, firstName.toString())
xmlData.saveChanges.setOnClickListener {
    Log.e(TAG, "onCreateView: save changes button is running")
    uploadImage(xmlData)

onSNACK(xmlData)

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



    fun getUserData(view: View, st: String){


            firstN = firstName.text.toString()
         emailAdd = emailAddressOfUser.text.toString()
         phoneN = phoneNo.text.toString()
        address = addressOfUser.text.toString()
        dateOfBirth = birthdayid.text.toString()
        imageurl = st
        idOfuse = FireStoreC().getCurrentUID()

        val checkedId = view.radioGender.checkedRadioButtonId
        val radio: RadioButton = view.findViewById(checkedId)
        gender =  radio.text.toString()





        Log.d(TAG, firstN + emailAdd + phoneN + address)

        val userInfo = User(
            idOfuse,
            firstN,
            lastN,
            emailAdd,
            phoneN,
            address,
            gender,
            imageurl,
            dateOfBirth
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
            Log.e(TAG, "onActivityResult: $bitmap")
            ProfileImageView.setImageBitmap(bitmap)
        }
        else if (data == null){

            ProfileImageView.setImageResource(R.drawable.ic_baseline_edit_24)
            Log.d(TAG, "onActivityResult: image is not uploaded ")
        }
        else{


            ProfileImageView.setImageResource(R.drawable.ic_baseline_edit_24)
            Log.d(TAG, "onActivityResult: image is not uploaded ")
        }
    }


    private fun uploadImage(view: View)  {



        if (filepath != null) {
            val fileName = FireStoreC().getCurrentUID().toString() +".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(filepath)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                         imageUrlSt = it.toString()

                        Log.d(TAG, "uploadImage: url issss " + it.toString())
                        getUserData(view, it.toString())

                    }
                }

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)

                    getUserData(view, imageUrlSt)
                })
        }
        else{

            getUserData(view, imageUrlSt)
        }
    }

    fun checkUserData() : Boolean{
        return  true
    }

    fun onSNACK(view: View){
        //Snackbar(view)
        val snackbar = Snackbar.make(view, "Successfully Updated",
            Snackbar.LENGTH_LONG).setAction("Action", null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#61bced"))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.textSize = 10f
        snackbar.show()
    }

    fun getName(name : String) {
        nameOfperson = name
    }

}


