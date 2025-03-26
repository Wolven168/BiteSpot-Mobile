package com.rexdev.bitespot.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.main.activities.MainActivity
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Global
import com.rexdev.bitespot.functions.LoginReq
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var et_email : EditText
    private lateinit var et_password : EditText
    private lateinit var tv_totisnup : TextView
    private lateinit var btn_login : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.testlayout_login)
        Global.init(this)

        // Setting up the IDs
        et_email = findViewById(R.id.et_email_login)
        et_password = findViewById(R.id.et_password_login)
        tv_totisnup = findViewById(R.id.tv_gotosignup)
        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener{
            login(et_email.text.toString(), et_password.text.toString())
            val intent = Intent(this, MainActivity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }

        tv_totisnup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }
    }

    private fun login(email : String, password : String) {
        val loginReq = LoginReq(email = email, password = password)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(loginReq)
                Log.d("LoginDebug", "Response: $response") // Debugging line

                if (response.success) {  // Check success properly
                    Global.LOGGED = true
                    Global.USERNAME = response.user?.username
                    Global.ID = response.user?.id
                    Global.ACCESS = response.user?.access
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("LoginDebug", "Login failed: ${response}")
                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("LoginDebug", "Error: ${e.message}")
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}