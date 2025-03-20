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
import androidx.fragment.app.Fragment
import com.rexdev.bitespot.R
import com.rexdev.bitespot.activities.Login_Activity
import com.rexdev.bitespot.functions.Global

class Profile_Fragment : Fragment() {
    val g = Global()
    lateinit var btn_logout : Button
    lateinit var tv_username : TextView
    lateinit var tv_email : TextView
    lateinit var tv_location : TextView
    lateinit var iv_image : ImageView
    lateinit var sw_currentlocation : Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.testlayout_profile, container, false)
        btn_logout = view.findViewById(R.id.btn_logout)
        if (g.LOGGED == false) {
            btn_logout.text = "Sign Up/In"
        }
        btn_logout.setOnClickListener {
            if (g.LOGGED) {
                logout()
            } else {
                sign()
            }
        }
        return view
    }

    private fun logout() {
        g.LOGGED = false
        g.ID = null
        g.USERNAME = "Default"
        g.LATIDUDE = 0.00
        g.LONGITUDE = 0.00

        val intent = Intent(requireContext(), Login_Activity::class.java)
        startActivity(intent)

        activity?.finishAffinity() // This properly closes the app without crashing
    }

    private fun sign() {
        val intent = Intent(requireContext(), Login_Activity::class.java)
        startActivity(intent)

        activity?.finishAffinity() // This properly closes the app without crashing
    }

}