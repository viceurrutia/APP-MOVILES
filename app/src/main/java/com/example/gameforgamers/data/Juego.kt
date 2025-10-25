package com.example.gameforgamers.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable




@Serializable
@Entity(tableName = "juegos") // Cambiamos el nombre de la tabla
data class Juego(
    @PrimaryKey val id: Int,
    val nombre: String,
    val precio: Double,
    val plataforma: String, // Añadimos el campo de tu JSON
    val imagenUrl: String
)