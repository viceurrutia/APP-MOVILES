package com.example.gameforgamers

import com.example.gameforgamers.WeatherResponse
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
