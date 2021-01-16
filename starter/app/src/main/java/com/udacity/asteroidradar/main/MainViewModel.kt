package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.network.AsteroidApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _properties = MutableLiveData<ImageOfTheDay>()

    val properties: LiveData<ImageOfTheDay>
        get() = _properties

    init {
        getImageOfTheDay()
    }

    fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                _properties.value = AsteroidApi.RETROFIT_SERVICE.getProperties()
            } catch (e: Exception) {
                Log.d("omertest", "getImageOfTheDay: 123 + $e")
            }
        }

    }
}