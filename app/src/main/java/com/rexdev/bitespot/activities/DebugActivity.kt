package com.rexdev.bitespot.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.main.activities.MainActivity
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Global

class DebugActivity: AppCompatActivity() {
    lateinit var tv_username : TextView
    lateinit var et_username : EditText
    lateinit var btn_username : Button
    lateinit var tv_longitude : TextView
    lateinit var tv_latitude : TextView
    lateinit var et_longitude : EditText
    lateinit var et_latitude : EditText
    lateinit var btn_location : Button
    lateinit var tv_ip : TextView
    lateinit var et_ip : EditText
    lateinit var btn_ip : Button
    lateinit var btn_back : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testlayout_debug)
        Global.init(this)

        tv_username = findViewById(R.id.tv_username_debug)
        et_username = findViewById(R.id.et_username_debug)
        btn_username = findViewById(R.id.btn_submitusername_debug)
        tv_longitude = findViewById(R.id.tv_longitude_debug)
        tv_latitude = findViewById(R.id.tv_latitude_debug)
        et_longitude = findViewById(R.id.et_longitutde_debug)
        et_latitude = findViewById(R.id.et_latitude_debug)
        btn_location = findViewById(R.id.btn_submitlocation_debug)
        tv_ip = findViewById(R.id.tv_ip_debug)
        et_ip = findViewById(R.id.et_ip_debug)
        btn_ip = findViewById(R.id.btn_submitip_debug)
        btn_back = findViewById(R.id.btn_back_debug)
        val name = "Username: " + Global.USERNAME
        val longitude = "Longitude: " + Global.LONGITUDE.toString()
        val latitude = "Latitude: " + Global.LATITUDE.toString()
        val ip = "IP: " + Global.BASE_URL

        tv_username.text = name
        tv_longitude.text = longitude
        tv_latitude.text = latitude
        tv_ip.text = ip

        btn_username.setOnClickListener {
            val username = et_username.text.toString()
            changeName(username)
        }
        btn_location.setOnClickListener {
            val long = et_longitude.text.toString().toDouble()
            val lat = et_latitude.text.toString().toDouble()
            changeLoc(long, lat)
        }
        btn_ip.setOnClickListener {
            val changeIp = et_ip.text.toString()
            changeIp(changeIp)
        }
        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)  // Switch context using requireContext()
            startActivity(intent)
            finish()  // Finish the parent activity to prevent returning
        }
    }



    private fun changeName(name : String?) {
        if (name == null) {Global.USERNAME = Global.USERNAME}
        else {Global.USERNAME = name}
    }
    private fun changeLoc(long : Double?, lat : Double?) {
        if (long == null) {Global.LONGITUDE = Global.LONGITUDE}
        else {Global.LONGITUDE = long}
        if (lat == null) {Global.LATITUDE = Global.LATITUDE}
        else {Global.LATITUDE = lat}
    }
    private fun changeIp(ip : String?) {
        if (ip == null) {Global.BASE_URL = Global.BASE_URL}
        else {Global.BASE_URL = ip}
    }
}