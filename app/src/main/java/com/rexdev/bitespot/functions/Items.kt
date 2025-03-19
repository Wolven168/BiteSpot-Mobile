package com.rexdev.bitespot.functions

data class Location(
    val name : String,
    val location : String,
    val rating : Double,
    val totalRating : Int,
    val details : String?,
    val image : String?,
    val longitude : Double? = 0.00,
    val latitude : Double? = 0.00,
)

data class User(
    val username : String = "Default",
    val id : String?,
    val img : String?,
    val access : String?,
)

data class Comment(
    val username : String,
    val locImg : String?,
    val proImg : String?,
    val text : String,
    val rating : Int?
)

// ===== DATABASE CLASSES =====
// REQUEST CLASSES
data class LoginReq(
    val username : String?,
    val email : String?,
    val password : String,
)

data class SignupReq(
    val username: String?,
    val email: String?,
    val phoneNum : String?,
    val password: String,
)

// RESPONSE CLASSES
data class DefaultRes(
    val message : String?,
    val success :Boolean,
)

data class LoginRes(
    val data : User?,
    val message: String?,
    val success: Boolean,
)