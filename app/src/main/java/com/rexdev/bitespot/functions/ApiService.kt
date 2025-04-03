package com.rexdev.bitespot.functions

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")

    @POST("users/register")
    suspend fun signup(@Body registerReq: SignupReq): DefaultRes

    @POST("users/login")
    suspend fun login(@Body request: LoginReq): LoginRes

    @POST("comment")
    suspend fun comment(@Body request: Comment): DefaultRes

    @GET("comment/location/{id}")
    suspend fun getComments(@Path("id") id: String): List<Comment>

    @DELETE("comment/{id}")
    suspend fun deleteComments(@Path("id") id: String): DefaultRes

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: String): Location

    // Endpoint to get items within a 1km radius
    @GET("locations")
    fun getLocations(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Double
    ): Call<LocationResponse>


//    @GET("items/indexShopItems/{id}")
//    suspend fun getShopItems(@Path("id") s: String): Response<List<ShopItem>>







    /*
    // Comment out until I add DeleteRes and UpdateTicketREs
    // Example of a DELETE request
    @DELETE("tickets/{id}")
    suspend fun deleteTicket(@Path("id") ticketId: String): DeleteRes

    // Example of a PUT request
    @PUT("tickets/{id}")
    suspend fun updateTicket(@Path("id") ticketId: String, @Body request: UpdateTicketReq): UpdateTicketRes
     */
}