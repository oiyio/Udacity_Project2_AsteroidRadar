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
        viewModelScope.launch{
            mainRepository.refreshPictureOfDay()
            mainRepository.refreshAsteroidList()
        }

        getAsteroidList()
    }

    val pictureOfDay = mainRepository.pictureOfDay

    fun getAsteroidList(){
        viewModelScope.launch{
            val myList =  mainRepository.getAsteroidList()
            asteroidList.value = myList
        }
    }

    fun getAsteroidsOfToday(){
        viewModelScope.launch{
            val myList = mainRepository.getAsteroidsByDate(getDateToday())
            asteroidList.value = myList
        }
    }
}