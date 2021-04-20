package com.udacity.asteroidradar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.PictureOfDay

@Entity
data class TablePictureOfDay(
    @PrimaryKey val id: Int = 0,
    val date: String,
    val explanation: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)

fun TablePictureOfDay.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        date = this.date,
        explanation = this.explanation,
        mediaType = this.media_type,
        service_version = this.service_version,
        title = this.title,
        url = this.url
    )
}


fun PictureOfDay.asDatabaseModel(): TablePictureOfDay {
    return TablePictureOfDay(
        date = this.date,
        explanation = this.explanation,
        media_type = this.mediaType,
        service_version = this.service_version,
        title = this.title,
        url = this.url
    )
}
