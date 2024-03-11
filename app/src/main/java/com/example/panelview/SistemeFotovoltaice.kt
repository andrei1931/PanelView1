package com.example.panelview

import RecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class SistemeFotovoltaice : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sistemAdapter: RecyclerViewAdapter

    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sisteme_fotovoltaice)
        if (!FirebaseApp.getApps(this).any { it.name == "default" }) {
            FirebaseApp.initializeApp(this)
        }
        db = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.SF)
        recyclerView.layoutManager = LinearLayoutManager(this)
        sistemAdapter = RecyclerViewAdapter(emptyList())
        fetchData()
    }
    private fun fetchData() {
        db.collection("sistem")
            .get()
            .addOnSuccessListener { result ->
                val sistemList = mutableListOf<Sistem>()
                for (document in result) {
                    sistemList.add(
                        Sistem(
                            documentId = document.id,
                            locatie = document.data["locatie"].toString()
                        )
                    )
                }
                sistemAdapter.updateData(sistemList)
                recyclerView.adapter = sistemAdapter
            }
            .addOnFailureListener { exception ->
                Log.d("SistemeFotovoltaice", "Error getting documents: ", exception)
            }
    }
    }



