//New modern

package com.example.OrgaFood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.OrgaFood.R
//mport com.google.common.base.Verify
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login2.*

import java.util.concurrent.TimeUnit

class LoginActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storedVerificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(applicationContext, ProductsActivity::class.java))
            finish()
        }





        LoginButton.setOnClickListener {
            LoginOrgafood()
        }
        val other = findViewById<ImageButton>(R.id.other)


        forgot_passw.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password ?")
            val view = layoutInflater.inflate(R.layout.activity_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.fp_username)
            builder.setView(view)
            username.hint = "Enter your email address"
            builder.setPositiveButton("Reset") { _, _ ->
                forgotPassword(username)
            }
            builder.setNegativeButton("Close") { _, _ -> }
            builder.show()
        }


    }

    private fun forgotPassword(username: EditText) {
        if (username.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        else{
            finish()
        }

        Firebase.auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent.", Toast.LENGTH_SHORT).show()
                }
            }


    }

    fun onRegisterClick(view: View) {
        startActivity(Intent(this@LoginActivity2, RegisterActivity2::class.java))
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.slide_out_right)
    }


    fun onOtherClick(view: View) {
        other.setOnClickListener {
            startActivity(Intent(this@LoginActivity2, SignInActivity::class.java))

        }
    }


    private fun LoginOrgafood() : Boolean {
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

                            startActivity(Intent(this@LoginActivity2, ProductsActivity :: class.java ))
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





