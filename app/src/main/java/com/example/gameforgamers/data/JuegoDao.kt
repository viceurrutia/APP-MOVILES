package com.example.gameforgamers.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(juegos: List<Juego>)

    @Query("SELECT * FROM juegos")
    fun obtenerTodos(): Flow<List<Juego>>

    @Query("SELECT COUNT(*) FROM juegos")
    suspend fun contar(): Int
}