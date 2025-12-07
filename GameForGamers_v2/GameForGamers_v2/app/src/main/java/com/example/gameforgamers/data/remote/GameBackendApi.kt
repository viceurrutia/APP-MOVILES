package com.example.gameforgamers.data.remote

import com.example.gameforgamers.model.Game
import com.example.gameforgamers.model.Purchase // 👈 Importante: Asegúrate de tener creada la clase Purchase
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GameBackendApi {

    // ==========================================
    // 🎮 SECCIÓN DE JUEGOS (CRUD)
    // ==========================================

    // Obtener la lista de todos los juegos (para Catálogo y Admin)
    @GET("api/games")
    suspend fun getAllGames(): List<Game>

    // Crear un juego nuevo (Solo Admin)
    @POST("api/games")
    suspend fun createGame(@Body game: Game): Game

    // Actualizar un juego existente (Solo Admin - edita precio, stock, etc.)
    @PUT("api/games/{id}")
    suspend fun updateGame(
        @Path("id") id: Int,
        @Body game: Game
    ): Game

    // Eliminar un juego (Solo Admin)
    @DELETE("api/games/{id}")
    suspend fun deleteGame(
        @Path("id") id: Int
    )

    // ==========================================
    // 💰 SECCIÓN DE COMPRAS (VENTAS)
    // ==========================================

    // 1. Obtener TODAS las ventas (Para que el Admin vea los "Ingresos")
    @GET("api/purchases")
    suspend fun getAllPurchases(): List<Purchase>

    // 2. 🆕 Obtener SOLO las compras de un usuario específico (Para "Mis Compras")
    // Se conecta con el endpoint del backend: /api/purchases/my/{email}
    @GET("api/purchases/my/{email}")
    suspend fun getMyPurchases(@Path("email") email: String): List<Purchase>

    // 3. Guardar una nueva compra (Cuando el usuario da "Confirmar Compra")
    @POST("api/purchases")
    suspend fun createPurchase(@Body purchase: Purchase): Purchase
}