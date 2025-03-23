package com.rexdev.bitespot.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.rexdev.bitespot.R

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge() // Edge-to-edge support
        setContentView(R.layout.activity_main) // Make sure this layout exists
    }
}