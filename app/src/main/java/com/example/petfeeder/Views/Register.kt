package com.example.petfeeder.Views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.petfeeder.R
import com.example.petfeeder.Shared.SharedPreference
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Register : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var serial : EditText
    private lateinit var name : EditText
    private lateinit var mProgressView: View
    private val SPLASH_TIME_OUT:Long = 4000
    private lateinit var pet : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        initialize()
    }

    fun initialize(){
        serial =  findViewById(R.id.serial)
        name = findViewById(R.id.pet)
        mProgressView = findViewById(R.id.progress_login);
        pet = findViewById(R.id.pets)
        mProgressView.setVisibility(View.GONE);
    }

    fun save(){
        mProgressView.setVisibility(View.VISIBLE);
        Handler().postDelayed({
            val sharedPreference: SharedPreference = SharedPreference(this)
            val numserie = serial.editableText.toString()
            val petname = name.editableText.toString()
            val type = pet.editableText.toString()

            sharedPreference.save("servo", numserie)
            sharedPreference.save("pet", type)
            databaseReference = FirebaseDatabase.getInstance().getReference(numserie)
            databaseReference.child("pet").setValue(type)
            databaseReference.child("servo").setValue("off")
            databaseReference.child("nombre").setValue(petname)
                .addOnSuccessListener {
                    mProgressView.setVisibility(View.GONE);
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    mProgressView.setVisibility(View.GONE);
                }
        }, SPLASH_TIME_OUT)
    }
    fun click(v: View){
        save()
    }
}