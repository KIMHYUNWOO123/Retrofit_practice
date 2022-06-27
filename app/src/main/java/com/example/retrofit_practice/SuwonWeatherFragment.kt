package com.example.retrofit_practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_seoul_weather.*


class SuwonWeatherFragment : Fragment() {

    private var data: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getStringArrayList("data")
        }
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
        tvText.append("날씨\n")
        tvText.append("온도는 ${data?.get(0)}\n")
        tvText.append("기압는 ${data?.get(1)}\n")
        tvText.append("습도는 ${data?.get(2)}\n")
    }
}