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
import com.rexdev.bitespot.functions.SignupReq
import kotlinx.coroutines.launch

class Signup_Activity : AppCompatActivity(){
    private lateinit var et_username : EditText
    private lateinit var et_email : EditText
    private lateinit var et_password : EditText
    private lateinit var tv_tologin : TextView
    private lateinit var btn_signup : Button
    private val g = Global()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.testlayout_signup)

        et_username = findViewById(R.id.et_username_signup)
        et_email = findViewById(R.id.et_email_signup)
        et_password = findViewById(R.id.et_password_signup)
        tv_tologin = findViewById(R.id.tv_switchtologin)
        btn_signup = findViewById(R.id.btn_signup)
        btn_signup.setOnClickListener{
            signup(et_username.text.toString(), et_email.text.toString(), et_password.text.toString())
            val intent = Intent(this, Login_Activity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }

        tv_tologin.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }
    }

    private fun signup(username: String, email : String, password : String) {
        val signupReq = SignupReq(username = username, email = email, password = password, phoneNum = null)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.signup(signupReq)
                if (response.success) {
                    Toast.makeText(this@Signup_Activity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Save token and navigate
                } else {
                    Toast.makeText(this@Signup_Activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@Signup_Activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}