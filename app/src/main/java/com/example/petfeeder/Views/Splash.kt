package com.example.petfeeder.Views

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.petfeeder.R
import com.example.petfeeder.Register
import com.example.petfeeder.Shared.SharedPreference

class Splash : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreference: SharedPreference =SharedPreference(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        Handler().postDelayed({
                if (sharedPreference.getValueString("servo")!=false){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, Register::class.java))
                    finish()
                }
        }, SPLASH_TIME_OUT)
    }
}