package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val mainRepository = MainRepository(database)

    init {
        viewModelScope.launch{
            mainRepository.refreshPictureOfDay()
            mainRepository.refreshAsteroidList()
        }
    }

    val asteroidList = mainRepository.asteroidList

    val pictureOfDay = mainRepository.pictureOfDay
}