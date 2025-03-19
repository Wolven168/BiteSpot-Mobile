package com.rexdev.bitespot.functions

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.pm.PackageManager
import com.main.activities.Main_Activity

class Global : Application() {

    // Initialize variables (you can set initial values)
    var ID : String? = null
    var LONGITUDE : Double? = 0.00
    var LATIDUDE : Double? = 0.00

    // Function to check if the location permission is granted
    fun checkLocationPermission(context: Context): Boolean {
        return if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(context as Main_Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            false
        }
    }

    // Function to check if location services (GPS or network) are enabled
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Function to prompt the user to enable location if it's disabled
    fun promptForLocation(context: Context) {
        if (!isLocationEnabled(context)) {
            // You could navigate the user to the settings page
            val intent = android.content.Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Location is already enabled", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to get the device's current location
    fun getCurrentLocation(context: Context, onLocationReceived: (Double, Double) -> Unit) {
        if (checkLocationPermission(context)) {
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Pass the coordinates to the callback
                    onLocationReceived(location.latitude, location.longitude)
                } else {
                    // Location is not available
                    onLocationReceived(0.0, 0.0)
                }
            }
        } else {
            // Permission not granted, handle it appropriately
            onLocationReceived(0.0, 0.0)
        }
    }
}
