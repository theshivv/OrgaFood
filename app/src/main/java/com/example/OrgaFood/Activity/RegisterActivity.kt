package com.example.OrgaFood.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

//        val registerBtnId : Button = findViewById(R.id.registerBtnId)
//
//        registerBtnId.setOnClickListener(){
//            val intent = Intent(this@RegisterActivity, login :: class.java)
//            startActivity(intent)
//
//        }

findViewById<Button>(R.id.RegistrationBth).setOnClickListener {
   register()
}


    }

private fun validateRegis() : Boolean {
    val fName = findViewById<EditText>(R.id.PersonName)
    val lName = findViewById<EditText>(R.id.PersonsLastName)
    val email = findViewById<EditText>(R.id.EmailAddress)
    val password = findViewById<EditText>(R.id.Password)
    return when {
        TextUtils.isEmpty(fName.text.toString().trim{ it <= ' ' } ) ->{
            Toast.makeText(this, "Invalid First Name", Toast.LENGTH_LONG).show();
            false
        }
        TextUtils.isEmpty(lName.text.toString().trim{ it <= ' ' } ) ->{
            Toast.makeText(this, "Invalid Last Name", Toast.LENGTH_LONG).show();
            false
        }
        TextUtils.isEmpty(email.text.toString().trim{ it <= ' ' } ) ->{
            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
            false
        }
        TextUtils.isEmpty(password.text.toString().trim{ it <= ' ' } ) ->{
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_LONG).show();
            false
        }

        else -> {
        true
        }
    }
}

    private fun register (){
        val email = findViewById<EditText>(R.id.EmailAddress)
        val password = findViewById<EditText>(R.id.Password)
        val fName = findViewById<EditText>(R.id.PersonName)
        val lName = findViewById<EditText>(R.id.PersonsLastName)
        if(validateRegis()){
            val email = email.text.toString().trim{ it <= ' ' }
            val password = password.text.toString().trim{ it <= ' ' }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){  task ->
                    if(task.isSuccessful){
                      val fireUserid : FirebaseUser? = task.result!!.user

                        val user = fireUserid?.let {
                            User(
                                it.uid,
                                fName.text.toString().trim{ it <= ' ' },
                                lName.text.toString().trim{ it <= ' ' },
                                email
                            )
                        }
                        if (user != null) {
                            FireStoreC().registerUser(this@RegisterActivity,user)
                        }

                        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                        Firebase.auth.signOut()

                        finish()
                    }
                    else{
                        Toast.makeText(this,"Invalid data",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }



}