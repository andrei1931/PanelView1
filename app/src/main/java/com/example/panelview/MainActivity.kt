package com.example.panelview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buton: Button = findViewById(R.id.button)
        val user_name: EditText = findViewById(R.id.editTextTextPersonName2)
        val parola: EditText = findViewById(R.id.editTextTextPassword2)

        buton.setOnClickListener {
            val usr = user_name.text.toString()
            val pwd = parola.text.toString()
            aut(usr, pwd)
        }
    }

    fun aut(usr: String, pwd: String) {
        if (usr.isEmpty() || pwd.isEmpty()) {
            val myToast =
                Toast.makeText(applicationContext, "Introdu numele și parola", Toast.LENGTH_SHORT)
            myToast.show()
        } else {
            Intent(this, MainActivity2::class.java).also {
                startActivity(it)
            }
        }
    }

}