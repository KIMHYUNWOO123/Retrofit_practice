package com.example.retrofit_practice.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofit_practice.R
import kotlinx.android.synthetic.main.fragment_seoul_weather.*


class SeoulWeatherFragment : Fragment() {
    private var data: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getStringArrayList("data")
        }
        Log.d("Seoul", "onCreate:$data ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seoul_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvText.append("날씨\n")
        tvText.append("온도는 ${data?.get(0)}\n")
        tvText.append("기압는 ${data?.get(1)}\n")
        tvText.append("습도는 ${data?.get(2)}\n")
    }


}