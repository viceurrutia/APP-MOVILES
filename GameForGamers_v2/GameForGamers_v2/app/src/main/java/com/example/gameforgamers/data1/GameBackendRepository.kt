package com.example.gameforgamers.data1

import com.example.gameforgamers.data.remote.GameBackendApi
import com.example.gameforgamers.model.Game
import com.example.gameforgamers.model.Purchase // 👈 Importar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameBackendRepository {

    // IMPORTANTE: Recuerda que si usas emulador es 10.0.2.2
    private const val BASE_URL = "http://10.15.231.118:8080/"
                                
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
        api.deleteGame(game.id)
    }

    // 🔽🔽 NUEVAS FUNCIONES PARA COMPRAS 🔽🔽
    suspend fun getPurchases(): List<Purchase> = api.getAllPurchases()

    suspend fun savePurchase(p: Purchase): Purchase = api.createPurchase(p)
}