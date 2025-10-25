package com.example.gameforgamers.data

import android.content.Context
import androidx.room.*

@Database(entities = [Juego::class], version = 1) // ¡Actualizado!
abstract class AppDatabase : RoomDatabase() {
    abstract fun juegoDao(): JuegoDao // ¡Actualizado!

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "game_store.db") // Nombre de BD cambiado
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}