package com.example.panelview

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.panelview.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.osmdroid.views.overlay.Marker


class HartaSisteme : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harta_sisteme)
        val mapView: MapView
        mapView = findViewById(R.id.mapView)

        val ctx = applicationContext
        Configuration.getInstance().load(ctx, getPreferences(MODE_PRIVATE))

        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userCurent = user.uid
            val geoPointQuery = db.collection("profiles").document(userCurent).collection("sis") // Replace with your collection name
                .get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) { // Check if there are any documents in the collection
                        for (document in result) {
                            var coordinates: org.osmdroid.util.GeoPoint? = null

                            // Try to retrieve 'coordonate' as a GeoPoint first
                            val geoPoint = document.getGeoPoint("coordonate")
                            if (geoPoint != null) {
                                val osmdroidGeoPoint = com.google.firebase.firestore.GeoPoint(
                                    geoPoint.latitude,
                                    geoPoint.longitude
                                )
                                coordinates = org.osmdroid.util.GeoPoint(geoPoint!!.latitude, geoPoint.longitude)

                            } else {
                                // If that fails, try to retrieve 'coordonate' as a String
                                val coordinatesString = document.getString("coordonate")
                                if (coordinatesString != null && coordinatesString is String) {
                                    val matches =
                                        Regex("""([0-9]+\.[0-9]+)°([NS])[^\d\.]*(?:\s*([0-9]+\.[0-9]+)°([EW]))""").find(
                                            coordinatesString
                                        )

                                    matches?.let {
                                        val (latDegrees, latDirection, lonDegrees, lonDirection) = matches.destructured
                                        val latitude = latDegrees.toDoubleOrNull()
                                        val longitude = lonDegrees.toDoubleOrNull()

                                        if (latitude != null && longitude != null) {
                                            // Adjust latitude and longitude based on direction
                                            val finalLatitude =
                                                if (latDirection == "N") latitude else -latitude
                                            val finalLongitude =
                                                if (lonDirection == "E") longitude else -longitude

                                            coordinates = org.osmdroid.util.GeoPoint(
                                                finalLatitude,
                                                finalLongitude
                                            )

                                            Log.d("Locatii", "Coordonatele: $finalLatitude, $finalLongitude")
                                        } else {
                                            Log.e(
                                                "Harta",
                                                "Invalid coordinates found for document: ${document.id}"
                                            )
                                        }
                                    }
                                }
                            }

                            if (coordinates != null) {
                                val locationName = document.getString("locatie")
                                val marker = Marker(mapView)
                                marker.position = coordinates
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                marker.title =
                                    locationName ?: "Unknown Location" // Provide a default name if null
                                mapView.overlays.add(marker)
                            }
                        }

                        // Adjust map center and zoom

// Adjust map center and zoom
                        if (mapView.overlays.isNotEmpty()) {
                            val lastOverlay = mapView.overlays.last()
                            if (lastOverlay is Marker) {
                                mapView.controller.setCenter(lastOverlay.position)
                                mapView.controller.setZoom(7.0)
                            }
                        }
                    }
                }
        }
    }
}
