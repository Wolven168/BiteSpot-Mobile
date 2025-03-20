package com.rexdev.bitespot.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.main.activities.Main_Activity
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Global
import com.rexdev.bitespot.functions.LoginReq
import com.rexdev.bitespot.functions.RetrofitClient
import kotlinx.coroutines.launch

class Login_Activity : AppCompatActivity() {
    private lateinit var et_email : EditText
    private lateinit var et_password : EditText
    private lateinit var tv_totisnup : TextView
    private lateinit var btn_login : Button
    private val g = Global()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.testlayout_login)

        // Setting up the IDs
        et_email = findViewById(R.id.et_email_login)
        et_password = findViewById(R.id.et_password_login)
        tv_totisnup = findViewById(R.id.tv_gotosignup)
        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener{
            login(et_email.text.toString(), et_password.text.toString())
            val intent = Intent(this, Main_Activity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }

        tv_totisnup.setOnClickListener {
            val intent = Intent(this, Signup_Activity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }
    }

    private fun login(email : String, password : String) {
        val loginReq = LoginReq(username = null, email = email, password = password)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(loginReq)
                if (response.success) {
                    g.LOGGED = true
                    g.USERNAME = response.data?.username
                    g.ID = response.data?.id
                    g.ACCESS = response.data?.access
                    Toast.makeText(this@Login_Activity, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Save token and navigate
                } else {
                    Toast.makeText(this@Login_Activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@Login_Activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}