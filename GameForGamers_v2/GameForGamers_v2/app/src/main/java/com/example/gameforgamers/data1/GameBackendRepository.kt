package com.example.gameforgamers.data1

import com.example.gameforgamers.data.remote.GameBackendApi
import com.example.gameforgamers.model.Game
import com.example.gameforgamers.model.Purchase // 👈 Importar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameBackendRepository {

    // IMPORTANTE: Recuerda que si usas emulador es 10.0.2.2
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val api: GameBackendApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GameBackendApi::class.java)
    }

    suspend fun getAll(): List<Game> = api.getAllGames()

    suspend fun create(game: Game): Game = api.createGame(game)

    suspend fun update(game: Game): Game = api.updateGame(game.id, game)

    suspend fun delete(game: Game) {
        val response = api.deleteGame(game.id)
        // Si no es exitosa (ej. 404 o 500), lanzamos error para que el Catch lo capture
        if (!response.isSuccessful) {
            throw Exception("Error al eliminar: ${response.code()}")
        }
        // Si es exitosa (204), no hacemos nada y la función termina bien.
    }

    // 🔽🔽 NUEVAS FUNCIONES PARA COMPRAS 🔽🔽
    suspend fun getPurchases(): List<Purchase> = api.getAllPurchases()

    suspend fun getMyPurchases(email: String): List<Purchase> = api.getMyPurchases(email)

    suspend fun savePurchase(p: Purchase): Purchase = api.createPurchase(p)
}