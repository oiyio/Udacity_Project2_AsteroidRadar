package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.domain.PictureOfDay
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(RequestInterceptor())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()


interface AsteroidApiService {

    @GET("planetary/apod")
    suspend fun getPictureOfDay(): PictureOfDay

    @GET("neo/rest/v1/feed")
    suspend fun getNearAsteroidList(@Query("start_date") dateToday: String): String

    /* * https://api.nasa.gov/planetary/apod?api_key=XuPUs0qRudQjLk2Zbr1t21n766Sbl1SmnCP1aZBH
     *

     * https://api.nasa.gov/neo/rest/v1/feed?start_date=2021-01-16&api_key=XuPUs0qRudQjLk2Zbr1t21n766Sbl1SmnCP1aZBH
     * */
}
object AsteroidApi {
    val RETROFIT_SERVICE : AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }
}

const val API_KEY = "XuPUs0qRudQjLk2Zbr1t21n766Sbl1SmnCP1aZBH"
