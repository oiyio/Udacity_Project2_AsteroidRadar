package com.udacity.asteroidradar.util

import java.text.SimpleDateFormat
import java.util.*


fun getDateToday() : String{
    val currentTime = Calendar.getInstance()
    val time = currentTime.time.day

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val dateToday = sdf.format(Calendar.getInstance().time)
    return dateToday
}