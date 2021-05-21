//New modern

package com.example.OrgaFood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.OrgaFood.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*

class LoginActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)


        LoginButton.setOnClickListener {
            LoginOrgafood()
        }

    }

    fun onRegisterClick(view: View) {
        startActivity(Intent(this, RegisterActivity2 ::class.java))
        overridePendingTransition(R.anim.slide_in_right,android.R.anim.slide_out_right)

    }

    fun LoginOrgafood() : Boolean {
        val lEmail = findViewById<EditText>(R.id.editTextEmail)
        val lPassword = findViewById<EditText>(R.id.editTextPassword)
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

                Toast.makeText(this,"successfulll1 " ,Toast.LENGTH_SHORT).show()
                val email : String = editTextEmail.text.toString().trim { it <= ' ' }
                val password : String = editTextPassword.text.toString().trim{ it <= ' '}

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){  task ->

                        if (task.isSuccessful) {
//                             val firebaseUser: FirebaseUser = task.result!!.user!!

                            startActivity(Intent(this, DashboardHomeScreen :: class.java ))
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