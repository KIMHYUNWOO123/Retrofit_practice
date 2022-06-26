package com.example.retrofit_practice

import com.google.gson.annotations.SerializedName

data class ReturnDateModel(
    val main : weatherBody
    )

data class weatherBody(
    val temp : String,
    val pressure : String,
    val humidity : String
)

