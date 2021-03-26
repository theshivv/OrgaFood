package com.example.OrgaFood.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.OrgaFood.R

//import android.appompat.widget.AppCompatTextView




class IntroPopup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_popup)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            //Do something after 100ms
                   startActivity(Intent(this@IntroPopup, login:: class.java ))
            finish()

        }, 2500)





//        val typeface = resources.getFont(R.font.myfont)
//        textView.typeface = typeface
//        val typeface: Typeface =  Typeface.createFromAsset(assets, "Montserrat-Regular.ttf")


//        appIntro.typeface = typeface
        var topAnimation: Animation
        var   bottomAnimtion  : Animation
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
       bottomAnimtion= AnimationUtils.loadAnimation(this, R.anim.bottom_animation)



        //adding hooks
        val ima: ImageView =findViewById(R.id.imageView3)
         val title : TextView = findViewById(R.id.introName)
        ima.setAnimation(topAnimation)
        title.setAnimation(bottomAnimtion)
    }

}