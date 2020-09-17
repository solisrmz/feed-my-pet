package com.example.petfeeder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.petfeeder.Shared.SharedPreference
import com.example.petfeeder.Views.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register.*

class Register : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var serial : EditText
    private lateinit var ssid : EditText
    private lateinit var password : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        initialize()
    }
    fun initialize(){
        serial =  findViewById(R.id.serial)
        ssid = findViewById(R.id.ssid)
        password = findViewById(R.id.pass)
    }

    fun save(){
        val sharedPreference: SharedPreference = SharedPreference(this)
        val numserie = serial.editableText.toString()
        sharedPreference.save("servo", numserie)
        databaseReference = FirebaseDatabase.getInstance().getReference(numserie)
        val sid = ssid.editableText.toString()
        val pass = password.editableText.toString()
        databaseReference.child("ssid").setValue(sid)
        databaseReference.child("password").setValue(pass)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun click(v: View){
        save()
    }
}