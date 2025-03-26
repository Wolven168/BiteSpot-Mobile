package com.main.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.rexdev.bitespot.R
import com.rexdev.bitespot.fragments.MainFragment
import com.rexdev.bitespot.fragments.ProfileFragment
import com.rexdev.bitespot.fragments.SearchFragment
import com.rexdev.bitespot.functions.Global

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testlayout_main)

        val btn_home = findViewById<Button>(R.id.btn_home)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_profile = findViewById<Button>(R.id.btn_profile)

        // Initialize SharedPreferences first
        Global.init(this)
        Log.d("GlobalDebug", "Username on startup: ${Global.USERNAME}")
        Log.d("GlobalDebug", "ID on startup: ${Global.ID}")
        Log.d("GlobalDebug", "Access on startup: ${Global.ACCESS}")
        Log.d("GlobalDebug", "Logged on startup: ${Global.LOGGED}")

            // Button click listeners to switch fragments
        btn_home.setOnClickListener {
            loadFragment(MainFragment())
        }

        btn_search.setOnClickListener {
            loadFragment(SearchFragment())
        }

        btn_profile.setOnClickListener {
            loadFragment(ProfileFragment())
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView3, fragment)
        transaction.addToBackStack(null) // Optional if you want to navigate back
        transaction.commit()
    }
}
