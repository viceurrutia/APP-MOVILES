package com.example.gameforgamers.data.remote

import com.example.gameforgamers.model.Game
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GameBackendApi {

    @GET("api/games")
    suspend fun getAllGames(): List<Game>

    @POST("api/games")
    suspend fun createGame(@Body game: Game): Game

    @PUT("api/games/{id}")
    suspend fun updateGame(
        @Path("id") id: Int,
        @Body game: Game
    ): Game

    @DELETE("api/games/{id}")
    suspend fun deleteGame(
        @Path("id") id: Int
    )
}