package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.entity.asDatabaseModel
import com.udacity.asteroidradar.database.entity.asDomainModel
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val database: AsteroidDatabase) {

    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it?.asDomainModel()
        }

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.asteroidDao.getPictureOfDay()) {
            it?.asDomainModel()
        }

    suspend fun refreshAsteroidList() {
        withContext(Dispatchers.IO) {
            try {
                val str = AsteroidApi.RETROFIT_SERVICE.getNearAsteroidList()
                val jsonObject = JSONObject(str)
                val asteroidList: ArrayList<Asteroid> = parseAsteroidsJsonResult(jsonObject)
                val list = asteroidList.asDatabaseModel()
                list.forEach {
                    database.asteroidDao.insertAll(it)
                }
            }
            catch (e : java.lang.Exception){
                Log.d("omertest", "refreshAsteroidList: $e")
            }

        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val image = AsteroidApi.RETROFIT_SERVICE.getPictureOfDay()
                database.asteroidDao.insert(pictureOfDay = image.asDatabaseModel())
            }
            catch (e : Exception){
                Log.d("omertest", "refreshPictureOfDay: $e")
            }
        }
    }
}