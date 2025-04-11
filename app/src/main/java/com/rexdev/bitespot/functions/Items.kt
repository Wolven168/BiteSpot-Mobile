package com.rexdev.bitespot.functions

data class Location(
    val id: Int,
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rating: Double,
    val description: String?,
    val totalRating: Int,
    val image: String?,
    val linkAddress: String?,
    val actual_rating: Int
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

data class LocationRes(
    val message: String?,
    val data: Location?,
    val success: Boolean
)

data class LoginRes(
    val user : User?,
    val message: String?,
    val success: Boolean,
)


data class LocDataRes(
    val success: Boolean,
    val data: List<Location>,
    val message: String?
)

data class CommentReq(
    val loc_id : Int,
    val user_id : Int?,
    val text: String?,
    val rating: Int
)

// ===== MISC =====
data class LocationResponse(
    val success: Boolean,
    val data: LocationData
)

data class LocationData(
    val radius: String?,
    val user_location: UserLocation?,
    val filtered_locations: List<Location>?
)

data class UserLocation(
    val latitude: String?,
    val longitude: String?
)

data class DebugData(
    val location: String,
    val lat: Double,
    val lon: Double,
    val calculated_distance: Double,
    val within_radius: Boolean
)

