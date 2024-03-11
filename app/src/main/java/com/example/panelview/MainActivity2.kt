package com.example.panelview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val modificare: Button = findViewById(R.id.button3)
        val harta:Button=findViewById(R.id.button4)
      val sisteme:Button=findViewById(R.id.sisteme)
        val asf:Button=findViewById(R.id.asf)
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
        harta.setOnClickListener(){
            val coordonata_x = cx.text.toString().substringAfter(':').toDouble()
            val coordonata_y = cy.text.toString().substringAfter(':').toDouble()


            val intent = Intent(this, Harta::class.java)
            intent.putExtra("coordonata_x", coordonata_x)
            intent.putExtra("coordonata_y", coordonata_y)

            startActivity(intent)
        }
        sisteme.setOnClickListener(){
            Intent(this, SistemeFotovoltaice::class.java).also {
                startActivity(it) }
        }
        asf.setOnClickListener(){
            Intent(this,MainActivity4::class.java).also { startActivity(it) }
        }


    }
}