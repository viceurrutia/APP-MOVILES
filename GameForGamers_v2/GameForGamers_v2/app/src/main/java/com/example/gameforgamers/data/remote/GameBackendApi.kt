package com.example.gameforgamers.data.remote

import com.example.gameforgamers.model.Game
import com.example.gameforgamers.model.Purchase // 👈 Importante
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface GameBackendApi {

    // ==========================================
    // 🎮 SECCIÓN DE JUEGOS (Catálogo y Admin)
    // ==========================================

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
    ): Response<Unit>

    // ==========================================
    // 💰 SECCIÓN DE COMPRAS (Ventas e Historial)
    // ==========================================

    // 1. Obtener TODAS las ventas (Para el Admin -> Ingresos)
    @GET("api/purchases")
    suspend fun getAllPurchases(): List<Purchase>

    // 2. 🆕 Obtener SOLO las compras de un usuario (Para "Mis Compras")
    @GET("api/purchases/my/{email}")
    suspend fun getMyPurchases(@Path("email") email: String): List<Purchase>

    // 3. Registrar una compra nueva
    @POST("api/purchases")
    suspend fun createPurchase(@Body purchase: Purchase): Purchase

    // 🆕 GESTIÓN DE USUARIOS
    @GET("api/users")
    suspend fun getAllUsers(): List<com.example.gameforgamers.model.AppUser>

    @POST("api/users")
    suspend fun registerUser(@Body user: com.example.gameforgamers.model.AppUser): com.example.gameforgamers.model.AppUser
}