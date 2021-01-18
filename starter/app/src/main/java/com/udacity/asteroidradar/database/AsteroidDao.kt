package com.udacity.asteroidradar.database
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.database.entity.TableAsteroid
import com.udacity.asteroidradar.database.entity.TablePictureOfDay
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("select * from tableasteroid order by closeApproachDate asc")
    suspend fun getAsteroids(): List<TableAsteroid>

    @Query("select * from tableasteroid where closeApproachDate = :date_today")
    suspend fun getAsteroidsByDate(date_today : String): List<TableAsteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: TableAsteroid)

    @Query("select * from TablePictureOfDay order by date desc limit 1")
    fun getPictureOfDay(): LiveData<TablePictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDay: TablePictureOfDay)
}

@Database(entities = [TableAsteroid::class, TablePictureOfDay::class], version = 1)
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
