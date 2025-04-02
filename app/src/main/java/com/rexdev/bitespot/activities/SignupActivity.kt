package com.rexdev.bitespot.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Global
import com.rexdev.bitespot.functions.SignupReq
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var et_username: TextInputLayout
    private lateinit var et_email: TextInputLayout
    private lateinit var et_password: TextInputLayout
    private lateinit var et_repassword: TextInputLayout
//    private lateinit var tv_tologin: TextView
    private lateinit var btn_signup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.testlayout_signup)
        Global.init(this)

        et_username = findViewById(R.id.et_username_signup)
        et_email = findViewById(R.id.et_email_signup)
        et_password = findViewById(R.id.et_password_signup)
        et_repassword = findViewById(R.id.et_repassword_signup)
//        tv_tologin = findViewById(R.id.btn_login)
        btn_signup = findViewById(R.id.btn_signup)

        btn_signup.setOnClickListener {
            signup(
                et_username.editText?.text.toString(),
                et_email.editText?.text.toString(),
                et_password.editText?.text.toString(),
                et_repassword.editText?.text.toString()
            )
        }

//        tv_tologin.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    private fun signup(username: String, email: String, password: String, repassword: String) {
        if (password != repassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val signupReq = SignupReq(username = username, email = email, password = password, phoneNum = null)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.signup(signupReq)
                if (response.success) {
                    Toast.makeText(this@SignupActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignupActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}