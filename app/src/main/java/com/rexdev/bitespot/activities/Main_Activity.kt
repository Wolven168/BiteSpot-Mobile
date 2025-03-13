package com.main.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.main.fragments.Home_Fragment
import com.rexdev.bitespot.R
import com.rexdev.bitespot.fragments.Search_Fragment

class Main_Activity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testlayout_main)
        val btn_home = findViewById<Button>(R.id.btn_home)
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_profile = findViewById<Button>(R.id.btn_profile)

        // Load default fragment
        loadFragment(Home_Fragment())

        // Button click listeners to switch fragments
        btn_home.setOnClickListener {
            loadFragment(Home_Fragment())
        }

        btn_search.setOnClickListener {
            loadFragment(Search_Fragment())
        }

//        btn_profile.setOnClickListener {
//            loadFragment(Profile_Fragment())
//        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView3, fragment)
        transaction.addToBackStack(null) // Optional if you want to navigate back
        transaction.commit()
    }
}
