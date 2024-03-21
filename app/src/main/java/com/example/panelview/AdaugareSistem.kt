package com.example.panelview

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint

class AdaugareSistem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adaugare_sistem)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val idS:EditText=findViewById(R.id.editTextIDSistem)
        val locatie:EditText=findViewById(R.id.editTextLocatie)
        val latitudine:EditText=findViewById(R.id.editTextLatitudine)
        val longitudine:EditText=findViewById(R.id.editTextLongitudine)
        val adauga:Button=findViewById(R.id.adaugare)
        adauga.setOnClickListener(){
         adaugare(idS.text.toString(),locatie.text.toString(),latitudine.text.toString(),longitudine.text.toString())

        }

    }
    fun adaugare(idS:String,locatie:String,latitudine:String,longitudine:String){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
            val userId = user.uid
            val userRef = db.collection("profiles").
            document(userId).collection("sis").document(idS)
            val data = hashMapOf(
                "locatie" to locatie,
                "coordonate" to GeoPoint(latitudine.toDouble(),longitudine.toDouble())
            )
            userRef.set(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Merge", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Eroare", Toast.LENGTH_SHORT).show()
                    Log.e("N", "Eroare adaugare")
                }
        }
    }
}