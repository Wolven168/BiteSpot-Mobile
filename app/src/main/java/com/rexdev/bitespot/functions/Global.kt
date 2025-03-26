package com.rexdev.bitespot.functions

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.pm.PackageManager
import com.main.activities.MainActivity

object Global {
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("GlobalPrefs", Context.MODE_PRIVATE)
        }
    }

    var ID: Int?
        get() = sharedPreferences?.getInt("ID", -1)?.takeIf { it != -1 }
        set(value) {
            sharedPreferences?.edit()?.putInt("ID", value ?: -1)?.apply()
        }

    var USERNAME: String?
        get() = sharedPreferences?.getString("USERNAME", "Default")
        set(value) {
            sharedPreferences?.edit()?.putString("USERNAME", value)?.apply()
        }

    var ACCESS: Int?
        get() = sharedPreferences?.getInt("ACCESS", -1)?.takeIf { it != -1 }
        set(value) {
            sharedPreferences?.edit()?.putInt("ACCESS", value ?: -1)?.apply()
        }

    var LOGGED: Boolean
        get() = sharedPreferences?.getBoolean("LOGGED", false) ?: false
        set(value) {
            sharedPreferences?.edit()?.putBoolean("LOGGED", value)?.apply()
        }

    var LONGITUDE: Double
        get() = sharedPreferences?.getFloat("LONGITUDE", 0.00f)?.toDouble() ?: 0.00
        set(value) {
            sharedPreferences?.edit()?.putFloat("LONGITUDE", value.toFloat())?.apply()
        }

    var LATITUDE: Double
        get() = sharedPreferences?.getFloat("LATITUDE", 0.00f)?.toDouble() ?: 0.00
        set(value) {
            sharedPreferences?.edit()?.putFloat("LATITUDE", value.toFloat())?.apply()
        }

    var AREA_RANGE: Double
        get() = sharedPreferences?.getFloat("AREA_RANGE", 1.00f)?.toDouble() ?: 1.00
        set(value) {
            sharedPreferences?.edit()?.putFloat("AREA_RANGE", value.toFloat())?.apply()
        }

    var BASE_URL: String?
        get() = sharedPreferences?.getString("BASE_URL", "192.168.100.51")
        set(value) {
            sharedPreferences?.edit()?.putString("BASE_URL", value)?.apply()
        }

    // Function to check if the location permission is granted
    fun checkLocationPermission(context: Context): Boolean {
        return if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            false
        }
    }

    // Function to check if location services are enabled
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Function to prompt the user to enable location if it's disabled
    fun promptForLocation(context: Context) {
        if (!isLocationEnabled(context)) {
            val intent =
                android.content.Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Location is already enabled", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to get the device's current location
    fun getCurrentLocation(context: Context, onLocationReceived: (Double, Double) -> Unit) {
        if (checkLocationPermission(context)) {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    onLocationReceived(location.latitude, location.longitude)
                } else {
                    onLocationReceived(0.0, 0.0)
                }
            }
        } else {
            onLocationReceived(0.0, 0.0)
        }
    }

    fun saveChanges() {
        sharedPreferences?.edit()?.apply()
    }
}
