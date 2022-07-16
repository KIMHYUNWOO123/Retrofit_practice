package com.example.retrofit_practice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_seoul_weather.*


class SuwonWeatherFragment : Fragment() {
    private var data: String? = null
    var temp : String? = null
    var feel: String? = null
    var humidity : String? = null
    var main : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("data")
        }
        Log.d("Suwon", data!!)
        var split = data?.split(",") ?: null
        temp = split?.get(0)?.substring(14)
        temp = (temp?.toFloat()!! - 273.00).toString()
        temp = temp?.substring(0,2)
        feel = split?.get(1)?.substring(12)
        feel = (feel?.toFloat()!! - 273.00).toString()
        feel = feel?.substring(0,2)
        humidity = split?.get(2)?.substring(10,12)
        main = split?.get(4)?.substring(13)
        var index = main?.length
        main = main?.substring(0, index!! - 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suwon_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("suwon", data.toString())
        mainText.text = main
        tempBar.apply {
            progress = temp?.toInt() ?: 0
            animation
            max = 60
        }
        tempText.append(" : $temp 도")
        feelBar.apply {
            progress = feel?.toInt() ?: 0
            animation = animation
            max = 60
        }
        feelText.append(" : $feel 도")
        humidityBar.apply {
            progress = feel?.toInt() ?: 0
            animation = animation
            max = 60
        }
        humidityText.append(" : $humidity %")
    }
}
