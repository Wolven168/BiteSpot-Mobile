package com.main.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
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

        val mainFragment = MainFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        loadFragment(mainFragment)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav1)
        bottomNav.setOnNavigationItemReselectedListener {  item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("FragmentDebug", "MainFragment is selected")
                    loadFragment(mainFragment)
                }
                R.id.navigation_search -> {
                    Log.d("FragmentDebug", "SearchFragment is selected")
                    loadFragment(searchFragment)
                }
                R.id.navigation_list -> {
                    Log.d("FragmentDebug", "ProfileFragment is selected")
                    loadFragment(profileFragment)
                }
            }
            true
        }

    }

    private fun loadFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
}
