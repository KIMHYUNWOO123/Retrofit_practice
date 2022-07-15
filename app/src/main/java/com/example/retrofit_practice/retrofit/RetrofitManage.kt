package com.example.retrofit_practice.retrofit

import android.util.Log
import com.example.retrofit_practice.utils.data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import java.lang.Thread.sleep


class RetrofitManage {
    val TAG = "Retrofit"
    var responseData : Response<ReturnDateModel>? = null
    companion object {
        val instance = RetrofitManage()
    }
    private var iRetrofit : OpenWeather? = RetrofitClient.getRetrofit(data.BASEURL)!!.create(OpenWeather :: class.java)

    fun getWeatherData(region : String) : Response<ReturnDateModel>? {
        Log.d("Region", region)
        var call = iRetrofit?.getWeather(region,data.KEY,data.LANGUAGE, data.UNIT)

        call?.enqueue(object : Callback<ReturnDateModel>
        {
            override fun onResponse (
                call: Call<ReturnDateModel>,
                response: Response<ReturnDateModel>
            ) {
                Log.d(TAG, "onResponse: $response")
                responseData = response
            }

            override fun onFailure(call: Call<ReturnDateModel>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }
        })
        return responseData
    }
}