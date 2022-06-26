package com.example.retrofit_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
    lateinit var data:ReturnDateModel
    private var myThread : MyThread? = null
    lateinit var myHandler : MyHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myHandler = MyHandler()

        button_seoul.setOnClickListener {
            myThread = MyThread("seoul")
            myThread!!.start()
        }
        button_suwon.setOnClickListener {
            myThread = MyThread("suwon")
            myThread!!.start()
        }
    }
    inner class MyThread(regionData : String) : Thread() {
        var region = regionData
        var key = "218c73d60692af6965fa11c043c3bf2d"
        var lang = "kr"
        var unit = "metric"
        override fun run(){
            var retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var testRetrofit : testRetrofit = retrofit.create(testRetrofit::class.java)
                testRetrofit.getWeather(region, key, lang, unit)?.enqueue(object: Callback<ReturnDateModel>{
                    override fun onResponse(
                        call: Call<ReturnDateModel>,
                        response: Response<ReturnDateModel>
                    ) {
                        data = response.body()!!
                        Log.d("성공", "onResponse: 성공")
                        Log.d("값", "onResponse:${data}")
                        Toast.makeText(this@MainActivity, "성공", Toast.LENGTH_LONG).show()
                        var temp = String.format("%.1f",(data.main.temp.toFloat()).minus(273.0))
                        var message : Message = Message.obtain()
                        when(region) {
                            "suwon" -> message.what = 0
                            "seoul" -> message.what = 1
                        }
                        message.arg1 = data.main.pressure.toInt()
                        message.arg2 = data.main.humidity.toInt()
                        message.obj = temp
                        myHandler.sendMessage(message)
                    }

                    override fun onFailure(call: Call<ReturnDateModel>, t: Throwable) {
                        Log.d("실패", "onFailure: $t")
                        Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
                    }
               })
        }
    }
    inner class MyHandler : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when(msg.what){
                1 -> {
                    textView.append("서울날씨\n")
                    textView.append("온도는 ${msg.obj}\n")
                    textView.append("기압은 ${msg.arg1}\n")
                    textView.append("습도은 ${msg.arg2}\n")
                }
                0 -> {
                    textView.append("수원날씨\n")
                    textView.append("온도는 ${msg.obj}\n")
                    textView.append("기압은 ${msg.arg1}\n")
                    textView.append("습도은 ${msg.arg2}\n")
                }
            }
        }
    }
}