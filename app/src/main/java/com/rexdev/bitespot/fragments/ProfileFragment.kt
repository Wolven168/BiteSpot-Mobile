package com.rexdev.bitespot.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.BinderThread
import androidx.fragment.app.Fragment
import com.rexdev.bitespot.R
import com.rexdev.bitespot.activities.DebugActivity
import com.rexdev.bitespot.activities.LoginActivity
import com.rexdev.bitespot.functions.Global

class ProfileFragment : Fragment() {
    lateinit var btn_logout: Button
    lateinit var tv_username: TextView
    lateinit var tv_location: TextView
    lateinit var iv_image: ImageView
    lateinit var sw_currentlocation: Switch
    lateinit var btn_debug: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.testlayout_profile, container, false)

        btn_logout = view.findViewById(R.id.btn_logout)
        tv_username = view.findViewById(R.id.tv_username)
        tv_location = view.findViewById(R.id.tv_location)

        sw_currentlocation = view.findViewById(R.id.sw_currentlocation)

        val username = "Username: " + Global.USERNAME
        val location = "Coordinates: " + Global.LONGITUDE + "," + Global.LATITUDE
        tv_username.text = username
        tv_location.text = location

        if (Global.LOGGED == false) {
            btn_logout.text = "Sign Up/In"
        }

        btn_logout.setOnClickListener {
            if (Global.LOGGED) {
                logout()
            } else {
                sign()
            }
        }

        btn_debug.setOnClickListener { debug() }

        // Switch to enable/disable GPS location updates
        sw_currentlocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableLocationTracking()
            } else {
                disableLocationTracking()
            }
        }

        return view
    }

    private fun enableLocationTracking() {
        if (Global.checkLocationPermission(requireContext())) {
            Global.getCurrentLocation(requireContext()) { latitude, longitude ->
                Global.LATITUDE = latitude
                Global.LONGITUDE = longitude
                tv_location.text = "Coordinates: $latitude, $longitude"
                Toast.makeText(requireContext(), "Location updated", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Location permission required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun disableLocationTracking() {
        Global.LATITUDE = 0.0
        Global.LONGITUDE = 0.0
        tv_location.text = "Coordinates: Not available"
        Toast.makeText(requireContext(), "Location tracking disabled", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        Global.LOGGED = false
        Global.ID = null
        Global.USERNAME = "Default"
        Global.LATITUDE = 0.00
        Global.LONGITUDE = 0.00

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()
    }

    private fun sign() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()
    }

    private fun debug() {
        val intent = Intent(requireContext(), DebugActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()
    }
}
