package com.example.petfeeder

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
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
        databaseReference = FirebaseDatabase.getInstance().getReference("esp32-1")
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
}