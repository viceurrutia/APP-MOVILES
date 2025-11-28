package com.example.gameforgamers.data.remote

import com.example.gameforgamers.data1.WeatherRepository
import com.example.gameforgamers.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String = "Santiago,cl",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = WeatherRepository.API_KEY
    ): WeatherResponse
}