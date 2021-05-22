package com.example.OrgaFood.Activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.FireStore.snakeBar
import com.example.OrgaFood.Activity.Fragements.filepath
import com.example.OrgaFood.Activity.Fragements.nameOfperson
import com.example.OrgaFood.Activity.Info.Product
import com.example.OrgaFood.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_upload_product.*
import kotlinx.android.synthetic.main.fragment_profile_view.*


class UploadProductActivity : AppCompatActivity() {


    var EnteredproductName = "sting"
    var EnterQuantity = "Quantity"
    var EnterPrice = "Price"
    var EnterSellerName = "sellerName"
    var ImageLink ="link"
    var pid =""
    lateinit var  imagePath : Uri
    lateinit var  firebaseStorage: FirebaseStorage
    lateinit var storageReference: StorageReference
    private  var ImageRequest = 111


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)

        val view: View = findViewById<View>(android.R.id.content).getRootView()

        firebaseStorage = FirebaseStorage.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
storageReference = FirebaseStorage.getInstance().reference



        val builder = Uri.Builder()
        builder.scheme("https")
            .authority("www.myawesomesite.com")
            .appendPath("turtles")
            .appendPath("types")
            .appendQueryParameter("type", "1")
            .appendQueryParameter("sort", "relevance")
            .fragment("section-name")
        imagePath = builder.build()


        upProductImage.setOnClickListener{
              selectImage()
        }


        uploadProduct.setOnClickListener {

            uploadImageOnFS(view)
            onSNACK(view)
}

    }

    private fun selectImage() {
     val intent = Intent()
        intent.type ="image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, ImageRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if(requestCode == ImageRequest && resultCode == RESULT_OK && data.data!=null){
                imagePath = data.data!!
                Picasso.with(this).load(imagePath).into(upProductImage)

            }
        }
    }

    private fun uploadImageOnFS(view: View)  {


        if (imagePath != null) {
            val fileName = FireStoreC().getCurrentUID().toString()+ enterProductName.text.toString() +".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/Products/$fileName")

            refStorage.putFile(imagePath)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        var imageUrlinkSt = it.toString()

                        Log.d(ContentValues.TAG, "uploadImage: url issss " + it.toString())
                       addProduct(it.toString(),fileName)

                    }
                }

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                    var messageOnSnak = "Please add the image"
                 snakeBar().onSNACK(view,messageOnSnak)

                })
        }
        else{


        }
    }

    fun addProduct(imglink: String,docname : String){
       EnteredproductName = enterProductName.text.toString()
        EnterQuantity = enterQuantity.text.toString()
        EnterPrice =enterPrice.text.toString()
        EnterSellerName = nameOfSeller.text.toString()

        var productObj = Product(
            EnteredproductName,
            imglink,
            docname,
            EnterPrice,
            EnterSellerName
        )
        FireStoreC().uploadPoduct(productObj,docname)

    }

    fun onSNACK(view: View){
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, "Successfully Uploaded",
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#61bced"))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.textSize = 10f
        snackbar.show()
    }

    fun getName(name: String) {
        nameOfperson = name
    }
}