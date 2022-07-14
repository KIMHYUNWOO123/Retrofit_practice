package com.example.retrofit_practice.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofitClient : Retrofit? = null

    fun getRetrofit(baseUrl : String) : Retrofit?{
        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return  retrofitClient
    }

}