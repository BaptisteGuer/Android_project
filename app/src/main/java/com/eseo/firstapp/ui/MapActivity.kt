package com.eseo.firstapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.eseo.firstapp.R
import com.eseo.firstapp.data.LocalPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val  PERMISSION_REQUEST_LOCATION: Int = 9999
    private lateinit var maMap:GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    companion object{
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        maMap = googleMap
        maMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        findViewById<TextView>(R.id.locationText).text = getString(R.string.localisation_failure)
        requestPermission()
    }

    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }
    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    MaterialDialog(this).show {
                        title(R.string.localisation_title)
                        message(R.string.my_localisation_message)
                        positiveButton(R.string.yes) {
                            requestPermission()
                        }
                        negativeButton(R.string.no)
                    }
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
                .addOnSuccessListener { geoCode(it) }
                .addOnFailureListener {
                    Toast.makeText(this, "Localisation impossible", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun geoCode(location: Location?){
        if (location != null){
            val geocoder = Geocoder(this, Locale.getDefault())
            val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            if (results.isNotEmpty()) {
                maMap.clear()
                maMap.addMarker(MarkerOptions()
                    .position(LatLng(location.latitude, location.longitude))
                    .title("Ma localisation")
                )
            }
            val zoomLevel = 13.0f //This goes up to 21
            maMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), zoomLevel))
            LocalPreferences.getInstance(this).saveStringValue(results[0].getAddressLine(0))
            findViewById<TextView>(R.id.locationText).text = getString(R.string.localisation_text) + results[0].getAddressLine(0)
            if(LocalPreferences.getInstance(this).getSaveStringValue() != null){
                Toast.makeText(this, LocalPreferences.getInstance(this).getSaveStringValue(), Toast.LENGTH_SHORT).show()
            }
        }
       }

}