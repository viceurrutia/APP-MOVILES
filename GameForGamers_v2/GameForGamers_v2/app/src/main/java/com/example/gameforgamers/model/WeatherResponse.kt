package com.example.gameforgamers.model

data class WeatherMain(
    val temp: Float
)

data class WeatherResponse(
    val main: WeatherMain
)
