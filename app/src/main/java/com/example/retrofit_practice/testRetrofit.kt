package com.example.retrofit_practice

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call

interface testRetrofit {

    @FormUrlEncoded
    @POST("retrofit/")
    fun test(
        @Field("userid") userid: String,
        @Field("userpassword") userpassword : String,
        @Field("username") username : String,
        @Field("userbirth") userbirth : String,
    ) : Call<ReturnDateModel>

}