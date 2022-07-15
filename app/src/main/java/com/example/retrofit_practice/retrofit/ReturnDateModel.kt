package com.example.retrofit_practice.retrofit

data class ReturnDateModel(
    val main : MainBody,
    val weather : List<WeatherBody>
    )

data class MainBody(
    val temp : String,
    val feels_like : String,
    val humidity : String
    )

data class WeatherBody (
    val main : String,
    val description : String
    )


