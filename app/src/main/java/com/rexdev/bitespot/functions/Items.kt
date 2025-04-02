package com.rexdev.bitespot.functions

data class Location(
    val id: Int,
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rating: Double,
    val details: String?,
    val totalRating: Int,
    val image: String?,
    val link: String?,
)

data class User(
    val username : String = "Default",
    val id : Int?,
    val img : String?,
    val access : Int?,
)

data class Comment(
    val id : Int,
    val username : String,
    val locImg : String?,
    val proImg : String?,
    val text : String?,
    val rating : Int
)

// ===== DATABASE CLASSES =====
// REQUEST CLASSES
data class LoginReq(
    val email : String,
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
    val user : User?,
    val message: String?,
    val success: Boolean,
)

// ===== MISC =====
data class LocationResponse(
    val radius: String,
    val user_location: UserLocation,
    val debug_data: List<DebugData>,
    val filtered_locations: List<Location>
)

data class UserLocation(
    val latitude: Double,
    val longitude: Double
)

data class DebugData(
    val location: String,
    val lat: Double,
    val lon: Double,
    val calculated_distance: Double,
    val within_radius: Boolean
)

