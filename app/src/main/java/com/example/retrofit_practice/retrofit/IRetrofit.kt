package com.example.retrofit_practice.retrofit

import retrofit2.Call
import retrofit2.http.*

interface OpenWeather {
    @GET("/data/2.5/weather?")
    fun getWeather(
        @Query("q") region : String,
        @Query("APPID") key : String,
        @Query("lang") lang : String,
        @Query("unit") unit : String
    ) : Call<ReturnDateModel>
}