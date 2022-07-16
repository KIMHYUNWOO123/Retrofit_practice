package com.example.retrofit_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.Handler
import android.util.Log
import com.example.retrofit_practice.databinding.ActivityMainBinding
import com.example.retrofit_practice.retrofit.RetrofitManage
import com.example.retrofit_practice.retrofit.ReturnDateModel
import retrofit2.Response
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    var response : Response<ReturnDateModel>? = null
    private lateinit var binding: ActivityMainBinding
    private var myThread : MyThread? = null
    lateinit var myHandler :MyHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myHandler = MyHandler()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSeoul.setOnClickListener {
            Log.d("btn", "onCreate: ")
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

    private fun setDataFragment(fragment : androidx.fragment.app.Fragment , data : String) {
        val bundle = Bundle()
        bundle.putString("data", data)
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
        var region = regionData
        override fun run(){
            RetrofitManage.instance.getWeatherData(myHandler,region)
        }
    }
    inner class MyHandler : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var data: String = msg.obj.toString()
            when (msg.what) {
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