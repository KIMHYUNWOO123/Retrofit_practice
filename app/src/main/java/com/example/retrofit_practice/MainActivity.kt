package com.example.retrofit_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.retrofit_practice.fragment.BusanWeatherFragment
import com.example.retrofit_practice.fragment.SeoulWeatherFragment
import com.example.retrofit_practice.fragment.SuwonWeatherFragment
import com.example.retrofit_practice.databinding.ActivityMainBinding
import com.example.retrofit_practice.retrofit.OpenWeather
import com.example.retrofit_practice.retrofit.RetrofitManage
import com.example.retrofit_practice.retrofit.ReturnDateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var data: ReturnDateModel
    private var myThread : MyThread? = null
    lateinit var myHandler : MyHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myHandler = MyHandler()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSeoul.setOnClickListener {
            myThread = MyThread("seoul")
            myThread!!.start()

        }
        binding.btnSuwon.setOnClickListener {
            myThread = MyThread("suwon")
            myThread!!.start()
        }
        binding.btnBusan.setOnClickListener {
            myThread = MyThread("busan")
            myThread!!.start()
        }
    }

    private fun setDataFragment(fragment : androidx.fragment.app.Fragment , data : ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("data", data)
        fragment.arguments = bundle
        setSwitchFragment(fragment)
    }

    private fun setSwitchFragment(fragment : androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    inner class MyThread(regionData : String) : Thread() {
        var TAG = "Main"
        var region = regionData
        var key = "218c73d60692af6965fa11c043c3bf2d"
        var lang = "kr"
        var unit = "metric"
        override fun run(){
            var retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var testRetrofit : OpenWeather = retrofit.create(OpenWeather::class.java)
                testRetrofit.getWeather(region, key, lang, unit)?.enqueue(object:
                    Callback<ReturnDateModel> {
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
                            "busan" -> message.what = 2
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
//            var response = RetrofitManage.instance.getWeatherData(region)
//            Log.d(TAG, "run: $response")
        }
    }
    inner class MyHandler : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var data  = arrayListOf(msg.obj.toString(),msg.arg1.toString(),msg.arg2.toString())
            when(msg.what){
                0 -> {
                    setDataFragment(SuwonWeatherFragment(), data)
                }
                1 -> {
                    setDataFragment(SeoulWeatherFragment(), data)
                }
                2 -> {
                    setDataFragment(BusanWeatherFragment(), data)

                }
            }
        }
    }
}