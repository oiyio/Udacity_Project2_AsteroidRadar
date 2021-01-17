package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.network.AsteroidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val database: AsteroidDatabase) {

    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroidList() {
        withContext(Dispatchers.IO) {
            val str = AsteroidApi.RETROFIT_SERVICE.getNearAsteroidList()
            val jsonObject = JSONObject(str)
            val asteroidList: ArrayList<Asteroid> = parseAsteroidsJsonResult(jsonObject)
            val list = asteroidList.asDatabaseModel()
            list.forEach {
                database.asteroidDao.insertAll(it)
            }
        }
    }
}