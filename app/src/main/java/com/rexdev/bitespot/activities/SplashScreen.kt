package com.main.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rexdev.bitespot.R
import com.rexdev.bitespot.activities.SplashScreen2

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen) // Using the new XML layout

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load the fade-in animation
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // Apply the animation to the ImageView and TextView
        val splashImageView = findViewById<ImageView>(R.id.splash)
        val bitespotTextView = findViewById<TextView>(R.id.bitespot_text)

        splashImageView.startAnimation(fadeInAnimation)
        bitespotTextView.startAnimation(fadeInAnimation)

        // Delay for 2 seconds, then navigate to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, SplashScreen2::class.java)
            startActivity(intent)
            finish() // Close SplashScreen to prevent returning to it
        }, 3000) // 3 seconds delay
    }
}