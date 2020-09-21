package com.example.petfeeder.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import com.example.petfeeder.R
import com.example.petfeeder.Shared.SharedPreference
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditData : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name : EditText
    private lateinit var mProgressView: View
    private val TIME_OUT:Long = 4000
    private lateinit var pet : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_data)
        val actionbar = supportActionBar
        actionbar!!.title = "Mis datos"
        actionbar.setDisplayHomeAsUpEnabled(true)
        initialize()
    }

    fun initialize(){
        name = findViewById(R.id.pet)
        mProgressView = findViewById(R.id.progress_login);
        pet = findViewById(R.id.pets)
        mProgressView.setVisibility(View.GONE);
    }

    fun save(){
        mProgressView.setVisibility(View.VISIBLE);
        Handler().postDelayed({
            val sharedPreference: SharedPreference = SharedPreference(this)
            val petname = name.editableText.toString()
            val type = pet.editableText.toString()
            val numserie = sharedPreference.getString("servo").toString()

            sharedPreference.save("pet", type)
            sharedPreference.save("nombre", petname)
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
        }, TIME_OUT)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun click(v: View){
        save()
    }
}