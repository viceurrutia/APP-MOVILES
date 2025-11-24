package com.example.gameforgamers

data class WeatherMain(
    val temp: Float
)

data class WeatherResponse(
    val main: WeatherMain
)
