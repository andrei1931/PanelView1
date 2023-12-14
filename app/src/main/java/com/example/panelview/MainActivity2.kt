package com.example.panelview

import Senzori
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val modificare: Button = findViewById(R.id.button3)
        val nm: TextView = findViewById(R.id.Nume)
        val prnm: TextView = findViewById(R.id.Prenume)
        val inv: TextView = findViewById(R.id.Invertor)
        val rol: TextView = findViewById(R.id.Rol)
        val cx: TextView = findViewById(R.id.CoordonataX)
        val cy: TextView = findViewById(R.id.CoordonataY)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = it.uid
            db.collection("profiles").document(userId).get()
                .addOnSuccessListener { document ->
                    val data = document.data
                    nm.text = "Nume:${data?.get("nume")}"
                    prnm.text = "Prenume:${data?.get("prenume")}"
                    inv.text = "Invertor:${data?.get("invertor")}"
                    rol.text = "Rol:${data?.get("rol")}"
                    cx.text = "CoordonataX:${data?.get("cx")}"
                    cy.text = "CoordonataY:${data?.get("cy")}"
                    Log.d("Receptionat", "${document.id} => ${document.data}")
                }
                .addOnFailureListener { e ->
                    Log.w( "Error adding document", e)
                }
        }

        modificare.setOnClickListener(){
            Intent(this, ModificareProfil::class.java).also {
                startActivity(it) }
        }
    }
}