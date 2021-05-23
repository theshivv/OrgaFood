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
import android.widget.EditText
import android.widget.Toast
import com.example.OrgaFood.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
//mport com.google.common.base.Verify
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_google.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*

import java.util.concurrent.TimeUnit
import kotlin.math.sign

class LoginActivity2 : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 120

    }


    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        mAuth = FirebaseAuth.getInstance()












        /*var currentUser = mAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(applicationContext, ProductsActivity::class.java))
            finish()
        }*/





        other.setOnClickListener {
            signIn()
        }



        LoginButton.setOnClickListener {
            LoginOrgafood()
        }



        forgot_password.setOnClickListener {
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



    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            }
        }

    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this,DashboardHomeScreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)

                }
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
        startActivity(Intent(this, RegisterActivity2 ::class.java))
        overridePendingTransition(R.anim.slide_in_right,android.R.anim.slide_out_right)

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
                Toast.makeText(this,"Invalid password" ,Toast.LENGTH_SHORT).show()
                false
            }

            else -> {

                Toast.makeText(this,"Logging In" ,Toast.LENGTH_SHORT).show()
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





