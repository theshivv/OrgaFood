package com.example.OrgaFood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.OrgaFood.Activity.FireStore.FireStoreC
import com.example.OrgaFood.Activity.Info.User
import com.example.OrgaFood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register2.*

class RegisterActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        RegisterButton.setOnClickListener {
            register()
        }
        val other1 = findViewById<ImageButton>(R.id.other1)



        }


    fun onLoginClick(view: View) {
        startActivity(Intent(this, LoginActivity2 ::class.java))

    }


    private fun validateRegis() : Boolean {
        val fName = findViewById<EditText>(R.id.editTextName)
        val lName = findViewById<EditText>(R.id.editTextLastName)
        val email = findViewById<EditText>(R.id.editEmailName)
        val password = findViewById<EditText>(R.id.editTextPasswordRegister)
        return when {
            TextUtils.isEmpty(fName.text.toString().trim{ it <= ' ' } ) ->{
                Toast.makeText(this, "Please Enter Your First Name", Toast.LENGTH_LONG).show();
                false
            }
            TextUtils.isEmpty(lName.text.toString().trim{ it <= ' ' } ) ->{
                Toast.makeText(this, "Please Enter Your Last Name", Toast.LENGTH_LONG).show();
                false
            }
            TextUtils.isEmpty(email.text.toString().trim{ it <= ' ' } ) ->{
                Toast.makeText(this, "Please Enter Email Address ", Toast.LENGTH_LONG).show();
                false
            }
            TextUtils.isEmpty(password.text.toString().trim{ it <= ' ' } ) ->{
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show();
                false
            }

            else -> {
                true
            }
        }
    }
        fun register (){
        val fName = findViewById<EditText>(R.id.editTextName)
        val lName = findViewById<EditText>(R.id.editTextLastName)
        val email = findViewById<EditText>(R.id.editEmailName)
        val password = findViewById<EditText>(R.id.editTextPasswordRegister)
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
                            FireStoreC().registerUser(this@RegisterActivity2,user)
                        }

                        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
//                        Firebase.auth.signOut()

                        finish()
                    }
                    else{
                        Toast.makeText(this,"Invalid data",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }



}









