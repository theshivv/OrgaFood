package com.example.OrgaFood.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.OrgaFood.R
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val registerTextId = findViewById<TextView>(R.id.registerTextId)
        registerTextId.setOnClickListener{

            val intent = Intent(this@login, RegisterActivity :: class.java )
            startActivity(intent)


        }

        findViewById<Button>(R.id.loginBtn).setOnClickListener{

            loginCheck()


        }



    }

    private fun loginCheck() : Boolean {
        val lEmail = findViewById<EditText>(R.id.loginEmailAddress)
        val lPassword = findViewById<EditText>(R.id.loginPassword)
        return when {
            TextUtils.isEmpty(lEmail.text.toString().trim { it <= ' ' }) ->{
                Toast.makeText(this,"Invalid email id",Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(lPassword.text.toString().trim{ it <= ' '}) ->{
                Toast.makeText(this,"Invalid password " ,Toast.LENGTH_SHORT).show()
                false
            }

            else -> {
                val email : String = lEmail.text.toString().trim { it <= ' ' }
                val password : String = lPassword.text.toString().trim{ it <= ' '}

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){  task ->

                        if (task.isSuccessful) {
//                             val firebaseUser: FirebaseUser = task.result!!.user!!

                            startActivity(Intent(this@login, ProductsActivity :: class.java ))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Invalid Password or email " ,Toast.LENGTH_SHORT).show()
                        }
                    }
                true
            }
        }

    }


}