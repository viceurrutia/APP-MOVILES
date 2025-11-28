package com.example.gameforgamers.data1

import com.example.gameforgamers.data.remote.WeatherApi
import com.example.gameforgamers.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {

    // 👉 Pega aquí tu API key REAL de OpenWeatherMap
    //    (algo como "6f3a9c0b1d23456e9f8a7b1c2d3e4f5g")
    const val API_KEY = "49c207256a75ab3d8c3f31a9f6750170"

    // Ciudad fija para la app
    const val CITY = "Santiago,cl"

    private val api: WeatherApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApi::class.java)
    }

    // Devuelve la temperatura en °C o null si hay error
    suspend fun getCurrentTempCelsius(): Float? {
        return try {
            // usamos la ciudad fija; el resto usa los valores por defecto
            val resp: WeatherResponse = api.getWeather(city = CITY)
            resp.main.temp
        } catch (e: Exception) {
            e.printStackTrace() // opcional, pero ayuda a ver en Logcat
            null
        }
    }
}