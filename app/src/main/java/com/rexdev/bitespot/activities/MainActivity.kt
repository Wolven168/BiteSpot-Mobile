package com.main.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rexdev.bitespot.R
import com.rexdev.bitespot.fragments.MainFragment
import com.rexdev.bitespot.fragments.ProfileFragment
import com.rexdev.bitespot.fragments.SearchFragment
import com.rexdev.bitespot.functions.Global

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testlayout_main)

        // Initialize SharedPreferences first
        Global.init(this)
        Log.d("GlobalDebug", "Username on startup: ${Global.USERNAME}")
        Log.d("GlobalDebug", "ID on startup: ${Global.ID}")
        Log.d("GlobalDebug", "Access on startup: ${Global.ACCESS}")
        Log.d("GlobalDebug", "Logged on startup: ${Global.LOGGED}")

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav1)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(MainFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_search -> {
                    loadFragment(SearchFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_list -> {
                    loadFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // Optional if you want to navigate back
        transaction.commit()
    }
}
