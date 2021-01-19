package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.getDateToday
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val mainRepository = MainRepository(database)
    val filterType = MutableLiveData<FilterType>()

    init {
        viewModelScope.launch {
            mainRepository.refreshPictureOfDay()
            mainRepository.refreshAsteroidList()
        }

        filterType.value = FilterType.AsteroidsStartingFromToday
    }

    val pictureOfDay = mainRepository.pictureOfDay

    val asteroidListLiveData: LiveData<List<Asteroid>> = Transformations.switchMap(
        filterType
    ) {
        it?.let {
            when (it) {
                FilterType.AllSavedAsteroids -> {
                    mainRepository.getAllSavedAsteroids()
                }
                FilterType.AsteroidsOfToday -> {
                    mainRepository.getAsteroidsOfToday(getDateToday()) // today
                }
                else -> {
                    mainRepository.getAsteroids(getDateToday())
                }
            }
        }
    }
}

sealed class FilterType {
    object AllSavedAsteroids : FilterType()
    object AsteroidsOfToday : FilterType()
    object AsteroidsStartingFromToday : FilterType()

}