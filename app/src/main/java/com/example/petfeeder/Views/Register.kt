package com.example.petfeeder.Views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        initialize()
    }

    fun initialize(){
        serial =  findViewById(R.id.serial)
        name = findViewById(R.id.pet)
        mProgressView = findViewById(R.id.progress_login);
        mProgressView.setVisibility(View.GONE);
    }

    fun save(){
        mProgressView.setVisibility(View.VISIBLE);
        val sharedPreference: SharedPreference = SharedPreference(this)
        val numserie = serial.editableText.toString()
        sharedPreference.save("servo", numserie)
        databaseReference = FirebaseDatabase.getInstance().getReference(numserie)
        val petname = name.editableText.toString()
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
    }
    fun click(v: View){
        save()
    }
}