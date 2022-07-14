package com.example.retrofit_practice.retrofit

data class ReturnDateModel(
    val main : MainBody,
    val weather : weatherBody
    )

data class MainBody(
    val temp : String,
    val pressure : String,
    val humidity : String
    )

data class weatherBody (
    val main : String,
    val description : String
    )


