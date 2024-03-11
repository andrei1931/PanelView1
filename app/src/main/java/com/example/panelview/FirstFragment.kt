@file:Suppress("DEPRECATION")

package com.example.panelview

import RecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.panelview.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private var _binding: FragmentFirstBinding? = null
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        recyclerViewAdapter = RecyclerViewAdapter(emptyList())
        binding.recyclerView.adapter = recyclerViewAdapter
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        Log.d("SF", "FirebaseFirestoreSettings created")

        val db = FirebaseFirestore.getInstance().apply {
            settings = settings
            Log.d("SF", "FirebaseFirestore settings applied")
        }

        Log.d("SF", "FirebaseFirestore instance initialized")

        recyclerViewAdapter = RecyclerViewAdapter(emptyList())
        binding.recyclerView.adapter = recyclerViewAdapter

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }
        val userCurent=FirebaseAuth.getInstance().currentUser
        if(userCurent!=null) {
            val userId = userCurent.uid
            db.collection("profiles")
                .document(userId).
               collection("sis")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("SF", "Data retrieved successfully")
                    println(userId)
                    val sistemList = mutableListOf<Sistem>()

                    for (document in result) {

                        sistemList.add(
                            Sistem(
                                documentId = document.id,
                                locatie = document.data["locatie"].toString()
                            )
                        )
                    }

                    recyclerViewAdapter?.updateData(sistemList)
                }
                .addOnFailureListener { exception ->
                    Log.d("SF", "Error getting documents: ", exception)
                }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
