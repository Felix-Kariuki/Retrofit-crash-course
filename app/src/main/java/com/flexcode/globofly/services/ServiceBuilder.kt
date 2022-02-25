package com.flexcode.globofly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val URL = "http://10.0.2.2:9000/"

    //logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    //okhttp client
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)
    //retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //instance of retrofit
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
      return  retrofit.create(serviceType)
    }

    //path parameters: if you want to indentify a particular resource
    //eg: www.felix.com/users/47   //retrieve user with id 47

    //Query Parameters: If you want to sort or filter items
    //www.felix.com/users?count=3 // retrieve first 3 users
    //www.felix.com/users?occupation=doctor //retrieve all doctors

    //QueryMap: When you have a lot of Query params then use QueryMap
    //www.felix.com/users?count=3&country=India
    //Retrieve first three users who belongs to India

    //www.felix.com/users47?country=India
}