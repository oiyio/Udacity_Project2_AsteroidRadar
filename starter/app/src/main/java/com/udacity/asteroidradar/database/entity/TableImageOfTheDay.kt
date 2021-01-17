package com.udacity.asteroidradar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.ImageOfTheDay

@Entity
data class TableImageOfTheDay(
    @PrimaryKey val id: Int = 0,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)

fun TableImageOfTheDay.asDomainModel(): ImageOfTheDay {
    return ImageOfTheDay(
        date = this.date,
        explanation = this.explanation,
        hdurl = this.hdurl,
        media_type = this.media_type,
        service_version = this.service_version,
        title = this.title,
        url = this.url
    )
}


fun ImageOfTheDay.asDatabaseModel(): TableImageOfTheDay {
    return TableImageOfTheDay(
        date = this.date,
        explanation = this.explanation,
        hdurl = this.hdurl,
        media_type = this.media_type,
        service_version = this.service_version,
        title = this.title,
        url = this.url
    )
}
