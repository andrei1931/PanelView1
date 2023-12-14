package com.example.panelview

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val buton: Button = findViewById(R.id.button)
        val user_name: EditText = findViewById(R.id.editTextTextPersonName2)
        val parola: EditText = findViewById(R.id.editTextTextPassword2)
        val buton2: Button = findViewById(R.id.button2)


        buton.setOnClickListener {
            val usr = user_name.text.toString()
            val pwd = parola.text.toString()
            aut(usr, pwd)
        }
        buton2.setOnClickListener {
            Intent(this, MainActivity3::class.java).also {
                startActivity(it)
            }
        }
    }


    fun aut(usr: String, pwd: String) {
        if (usr.isEmpty() || pwd.isEmpty()) {
            val myToast =
                Toast.makeText(applicationContext, "Introdu emailul și parola", Toast.LENGTH_SHORT)
            myToast.show()
        } else {
            auth.signInWithEmailAndPassword(usr, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Intent(this, MainActivity2::class.java).also {
                            startActivity(it)

                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Email sau parola gresita",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }
}