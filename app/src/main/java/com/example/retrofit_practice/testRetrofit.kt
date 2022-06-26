package com.example.retrofit_practice

import retrofit2.Call
import retrofit2.http.*

interface testRetrofit {


    @GET("/data/2.5/weather?")
    fun getWeather(
        @Query("q") region : String,
        @Query("APPID") key : String,
        @Query("lang") lang : String,
        @Query("unit") unit : String
    ) : Call<ReturnDateModel>

}