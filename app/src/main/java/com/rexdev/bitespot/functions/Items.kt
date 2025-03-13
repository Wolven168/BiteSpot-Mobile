package com.rexdev.bitespot.functions

data class Location(
    val name : String,
    val location : String,
    val rating : Double,
    val totalRating : Int,
    val details : String?,
    val image : String?,
)

data class User(
    val username : String,
    val access : String
)

data class Comment(
    val username : String,
    val text : String,
    val rating : Int?
)
