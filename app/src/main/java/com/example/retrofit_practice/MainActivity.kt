package com.example.retrofit_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {
    var data:ReturnDateModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var key = "218c73d60692af6965fa11c043c3bf2d"
        var lang = "kr"
        var unit = "metric"
        var retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var testRetrofit : testRetrofit = retrofit.create(testRetrofit::class.java)
        button_seoul.setOnClickListener{
            var region = "seoul"
            testRetrofit.getWeather(region, key, lang, unit)?.enqueue(object: Callback<ReturnDateModel>{
                override fun onResponse(
                    call: Call<ReturnDateModel>,
                    response: Response<ReturnDateModel>
                ) {
                    data = response.body()
                    Log.d("성공", "onResponse: 성공")
                    Log.d("값", "onResponse:${data}")
                    Toast.makeText(this@MainActivity, "성공", Toast.LENGTH_LONG).show()
                    var temp = (data?.main?.temp?.toFloat())?.minus(273.0)
                    textView.append("온도는 ${String.format("%.1f",temp)}\n")
                    textView.append("기압은 ${data?.main?.pressure}\n")
                    textView.append("습도은 ${data?.main?.humidity}\n")
                }

                override fun onFailure(call: Call<ReturnDateModel>, t: Throwable) {
                    Log.d("실패", "onFailure: $t")
                    Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}