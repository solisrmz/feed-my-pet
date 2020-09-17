package com.example.petfeeder.Views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.petfeeder.R
import com.example.petfeeder.Shared.SharedPreference
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var estado: TextView
    private lateinit var feedme: Button
    private lateinit var servo: String
    private lateinit var name: String
    private lateinit var mProgressView: View
    private lateinit var button : Button
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference(this)
        val model = sharedPreference.getString("servo").toString()
        databaseReference = FirebaseDatabase.getInstance().getReference(model)
        initialize()
        loop()
    }

    fun feed(v: View){
        databaseReference.child("servo").setValue("on")
        estado.setText("")
    }

    fun initialize(){
        mProgressView = findViewById(R.id.progress);
        mProgressView.setVisibility(View.GONE);
        feedme = findViewById(R.id.feedme)
        estado = findViewById(R.id.estado)
        button = findViewById(R.id.feedme)
    }

    fun loop(){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                servo = dataSnapshot.child("servo").value.toString()
                if(servo == "off"){
                    getPet()
                    feedme.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE)
                }else{
                    getPet()
                    feedme.setVisibility(View.GONE)
                    mProgressView.setVisibility(View.VISIBLE);
                    name = dataSnapshot.child("nombre").value.toString()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("error", "Error!", databaseError.toException())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun getPet(){
        val petType = sharedPreference.getString("pet").toString()
        if (petType == "Gato" || petType == "gato"){
            animationView.setAnimation(R.raw.circlecat)
            animationView.playAnimation()
            animationView.loop(true)
            estado.setText("¡Hola humano!")
        }
        if (petType == "Perro" || petType == "perro"){
            animationView.setAnimation(R.raw.circledog)
            animationView.playAnimation()
            animationView.loop(true)
            estado.setText("¡Hola humano!")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.configuration-> {
                startActivity(Intent(this, Register::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}