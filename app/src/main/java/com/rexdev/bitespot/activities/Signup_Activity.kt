package com.rexdev.bitespot.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.rexdev.bitespot.R

class Signup_Activity : AppCompatActivity(){
    private lateinit var et_email : EditText
    private lateinit var et_password : EditText
    private lateinit var btn_signup : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.testlayout_signup)

        et_email = findViewById(R.id.et_email_signup)
        et_password = findViewById(R.id.et_password_signup)
        btn_signup = findViewById(R.id.btn_signup)
    }

}