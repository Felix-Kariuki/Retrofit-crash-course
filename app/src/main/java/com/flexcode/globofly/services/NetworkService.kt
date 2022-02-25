package com.flexcode.globofly.services

import com.flexcode.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //from the api @get/destinations
    @GET("destination")//to use 2 or more filters add "query map"
    fun getDestinationList(@Query("country") country: String?): Call<List<Destination>> //use query to filter if dont use null in the acivity

    //because of http://base-url/destination/47
    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination> //not list

    @GET("messages")
    fun getMessages(): Call<String>

    //in case fetching from another api
    @GET
    fun getMessage(@Url anotherUrl: String): Call<String>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination): Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") description: String,
        @Field("country") country: String

    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id")id: Int): Call<Unit>
}