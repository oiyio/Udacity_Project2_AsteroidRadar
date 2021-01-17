package com.udacity.asteroidradar.domain

import com.squareup.moshi.Json

data class PictureOfDay(
    val date: String,
    val explanation: String,
    val hdurl: String,
    @Json(name = "media_type") val mediaType: String,
    val service_version: String,
    val title: String,
    val url: String
)