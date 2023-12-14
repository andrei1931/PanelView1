package com.example.panelview

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ModificareProfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificare_profil)
        val mod_prof: Button = findViewById(R.id.button4)
        val n: EditText = findViewById(R.id.editTextNume)
        val p: EditText = findViewById(R.id.editTextPrenume)
        val i: EditText = findViewById(R.id.editTextInvertor)
        val r: EditText = findViewById(R.id.editTextRol)
        val x: EditText = findViewById(R.id.editTextCoordonataX)
        val y: EditText = findViewById(R.id.editTextCoordonataY)
        mod_prof.setOnClickListener() {
            upd(
                n.text.toString(),
                p.text.toString(),
                i.text.toString(),
                r.text.toString(),
                x.text.toString().toDouble(),
                y.text.toString().toDouble()
            )
        }
    }

    private fun upd(
        nume: String,
        prenume: String,
        invertor: String,
        rol: String,
        cx: Double,
        cy: Double
    ) {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            val userRef = db.collection("profiles").document(userId)
            userRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Documentul există, îl actualizăm
                    userRef.update(
                        "nume", nume,
                        "prenume", prenume,
                        "invertor",invertor,
                        "rol",rol,
                        "cx",cx,
                        "cy",cy
                    ).addOnSuccessListener {
                        Toast.makeText(this, "Profil actualizat cu succes!", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Eroare la actualizarea profilului: ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Documentul nu există, îl creăm
                    userRef.set(
                        mapOf(
                            "nume" to nume,
                            "prenume" to prenume,
                            "invertor" to invertor,
                            "rol" to rol,
                            "cx" to cx,
                            "cy" to cy
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(this, "Profil creat cu succes!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Eroare la crearea profilului: ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Eroare la obținerea profilului: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}