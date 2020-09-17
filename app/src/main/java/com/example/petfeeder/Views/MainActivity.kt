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
import com.example.petfeeder.R
import com.example.petfeeder.Shared.SharedPreference
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var estado: TextView
    private lateinit var feedme: Button
    private lateinit var servo: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreference: SharedPreference = SharedPreference(this)
        val model = sharedPreference.getString("servo").toString()
        databaseReference = FirebaseDatabase.getInstance().getReference(model)
        initialize()
    }

    fun feed(v: View){
        animationView.setAnimation(R.raw.dogeat)
        animationView.playAnimation()
        animationView.loop(true)
        databaseReference.child("servo").setValue("on")
        estado.setText("¡Gracias humano por alimentarme!")
    }

    fun initialize(){
        feedme = findViewById(R.id.feedme)
        estado = findViewById(R.id.estado)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                servo = dataSnapshot.child("servo").value.toString()
                if(servo == "off"){
                    estado.setText(" ")
                    animationView.setAnimation(R.raw.catload)
                    animationView.playAnimation()
                    animationView.loop(true)
                }else{

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