package com.example.gameforgamers.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class DataRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val dao = db.juegoDao() // ¡Actualizado!

    fun getJuegos(): Flow<List<Juego>> { // ¡Actualizado!
        return dao.obtenerTodos()
    }

    suspend fun checkAndPrepopulateDatabase() {
        if (dao.contar() == 0) {
            val json = Json { ignoreUnknownKeys = true }
            val jsonString = context.assets.open("juegos.json") // ¡Actualizado!
                .bufferedReader().use { it.readText() }

            val juegosDesdeJson = json.decodeFromString<List<Juego>>(jsonString) // ¡Actualizado!
            dao.insertarTodos(juegosDesdeJson) // ¡Actualizado!
        }
    }
}
