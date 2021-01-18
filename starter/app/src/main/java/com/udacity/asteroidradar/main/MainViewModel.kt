package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.entity.TableAsteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.util.getDateToday
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val mainRepository = MainRepository(database)

    val asteroidList = MutableLiveData<List<TableAsteroid>>()

    init {
        viewModelScope.launch {
            mainRepository.refreshPictureOfDay()
            mainRepository.refreshAsteroidList()
        }

        getAsteroidList()
    }

    val pictureOfDay = mainRepository.pictureOfDay

    /*
    * Get only today and future asteroids
    * */
    fun getAsteroidList() {
        viewModelScope.launch {
            val myList = mainRepository.getAsteroidList(getDateToday())
            asteroidList.value = myList
        }
    }

    /*
    * Get only today asteroids
    * */
    fun getAsteroidsOfToday() {
        viewModelScope.launch {
            val myList = mainRepository.getAsteroidsByDate(getDateToday())
            asteroidList.value = myList
        }
    }

    /*
    * Get all asteroids including past days
    * */
    fun getAllSavedAsteroids() {
        viewModelScope.launch {
            val myList = mainRepository.getAllSavedAsteroids()
            asteroidList.value = myList
        }
    }
}