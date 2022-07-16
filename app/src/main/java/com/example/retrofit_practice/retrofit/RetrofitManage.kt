package com.example.retrofit_practice.retrofit

import android.os.Handler
import android.os.Message
import android.util.Log
import com.example.retrofit_practice.MainActivity
import com.example.retrofit_practice.utils.data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import java.lang.Thread.sleep


class RetrofitManage {
    val TAG = "Retrofit"
    companion object {
        val instance = RetrofitManage()
    }
    private var iRetrofit : OpenWeather? = RetrofitClient.getRetrofit(data.BASEURL)!!.create(OpenWeather :: class.java)

    fun getWeatherData(myHandler: MainActivity.MyHandler, region : String){
        Log.d("Region", region)
        var call = iRetrofit?.getWeather(region,data.KEY,data.LANGUAGE, data.UNIT)

        call?.enqueue(object : Callback<ReturnDateModel>
        {
            override fun onResponse(
                call: Call<ReturnDateModel>,
                response: Response<ReturnDateModel>
            ) {
                Log.d("응답", "onResponse: $response")
                var message = Message.obtain()
                var body = response?.body()
                Log.d("바디", "onResponse: $body ")
                message.obj = "${body?.main},${body?.weather}"
                message.what =
                    when (region) {
                        "seoul" -> 1
                        "suwon" -> 0
                        "busan" -> 2
                        else -> 3
                    }
                myHandler.sendMessage(message)
            }

            override fun onFailure(call: Call<ReturnDateModel>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }
        })

    }
}