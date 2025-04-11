package com.rexdev.bitespot.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.activities.MainActivity
import com.rexdev.bitespot.R

class SplashScreen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton: Button = findViewById(R.id.login_button)
        val signupButton: Button = findViewById(R.id.signup_button)
        val bypassButton: Button = findViewById(R.id.bypass_button)

        loginButton.setOnClickListener {
            // Replace LoginActivity::class.java with your actual login activity
            val intent = Intent(this, LoginActivity::class.java) // Ensure LoginActivity exists
            startActivity(intent)
        }

        signupButton.setOnClickListener {
            // Replace SignupActivity::class.java with your actual signup activity
            val intent = Intent(this, SignupActivity::class.java) // Ensure SignupActivity exists
            startActivity(intent)
        }

        bypassButton.setOnClickListener {
            // Replace MainActivity::class.java with your actual main activity
            val intent = Intent(this, MainActivity::class.java) // Ensure MainActivity exists
            startActivity(intent)
        }
    }
}