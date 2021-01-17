package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.network.AsteroidApi
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _properties = MutableLiveData<ImageOfTheDay>()

    val properties: LiveData<ImageOfTheDay>
        get() = _properties

    private val database = getDatabase(application)
    private val mainRepository = MainRepository(database)

    init {
        getImageOfTheDay()

        viewModelScope.launch{
            mainRepository.refreshAsteroidList()
        }
    }

    val asteroidList = mainRepository.asteroidList

    fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                _properties.value = AsteroidApi.RETROFIT_SERVICE.getImageOfTheDay()
            } catch (e: Exception) {
                Log.d("omertest", "getImageOfTheDay: 123 + $e")
            }
        }
    }

}