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


class MainActivity : AppCompatActivity() {
    var data:ReturnDateModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var retrofit = Retrofit.Builder()
            .baseUrl("http:/172.30.1.24:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var testRetrofit : testRetrofit = retrofit.create(testRetrofit::class.java)
        btn.setOnClickListener{
            var id = id.text.toString()
            var pw = pw.text.toString()
            var name = name.text.toString()
            var birth = birth.text.toString()
            testRetrofit.test(id,pw,name, birth)?.enqueue(object: Callback<ReturnDateModel>{
                override fun onResponse(
                    call: Call<ReturnDateModel>,
                    response: Response<ReturnDateModel>
                ) {
                    data = response.body()
                    Log.d("성공", "onResponse: 성공")
                    Log.d("값", "onResponse:$data ")
                    Toast.makeText(this@MainActivity, "성공", Toast.LENGTH_LONG).show()
                    code.text = data?.code
                    msg.text = data?.msg
                }

                override fun onFailure(call: Call<ReturnDateModel>, t: Throwable) {
                    Log.d("실패", "onFailure: $t")
                    Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}