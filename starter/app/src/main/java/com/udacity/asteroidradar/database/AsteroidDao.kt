package com.udacity.asteroidradar.database
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.database.entity.TableAsteroid
import com.udacity.asteroidradar.database.entity.TableImageOfTheDay

@Dao
interface AsteroidDao {
    @Query("select * from tableasteroid order by closeApproachDate asc")
    fun getAsteroids(): LiveData<List<TableAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: TableAsteroid)

    @Query("select * from TableImageOfTheDay order by date desc limit 1")
    fun getImageOfTheDay(): LiveData<TableImageOfTheDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageOfTheDay: TableImageOfTheDay)
}

@Database(entities = [TableAsteroid::class, TableImageOfTheDay::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, AsteroidDatabase::class.java, "asteroid.db").build()
        }
    }
    return INSTANCE
}
