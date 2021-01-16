package com.udacity.asteroidradar.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.MarsApi
import com.udacity.asteroidradar.network.MarsProperty
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

/*    private val _properties = MutableLiveData<List<MarsProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<MarsProperty>>
        get() = _properties*/

    private val _properties = MutableLiveData<ImageOfTheDay>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<ImageOfTheDay>
        get() = _properties

    init {
        getImageOfTheDay()
    }
    fun getImageOfTheDay(){
        viewModelScope.launch {
            try {
                _properties.value = AsteroidApi.RETROFIT_SERVICE.getProperties()
            } catch (e: Exception) {
                Log.d("omertest", "getImageOfTheDay: 123 + $e")
            }
        }

       /* viewModelScope.launch {
            try {
                _properties.value = MarsApi.retrofitService.getProperties()
            } catch (e: Exception) {
                Log.d("omertest", "getImageOfTheDay: 123 + $e")
            }
        }*/
    }
}