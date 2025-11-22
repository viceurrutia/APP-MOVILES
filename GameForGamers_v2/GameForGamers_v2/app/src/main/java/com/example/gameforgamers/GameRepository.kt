package com.example.gameforgamers

import com.example.gameforgamers.model.Game

object GameRepository {

    // Ajusta los juegos y nombres de drawable a los que ya tienes
    private val games = mutableListOf(
        Game(1, "Zelda TOTK", "$59.990", "zelda", "Aventura", 80, "Exploración mundo abierto", originalPrice = "$69.990", discountPercent = 15, stock = 15),
        Game(2, "Elden Ring", "$49.990", "eldenring", "RPG", 100, "RPG desafiante", originalPrice = "$59.990", discountPercent = 20, stock = 10),
        Game(3, "Super Mario", "$39.990", "mario", "Plataformas", 20, "Clásico plataformas", stock = 25)
        // agrega más si quieres
    )

    private var nextId = games.maxOfOrNull { it.id }?.plus(1) ?: 1

    fun all(): MutableList<Game> = games.toMutableList()

    fun add(game: Game) {
        games.add(game)
    }

    fun delete(game: Game) {
        games.remove(game)
    }

    fun incStock(game: Game) {
        val idx = games.indexOfFirst { it.id == game.id }
        if (idx != -1) games[idx].stock++
    }

    fun decStock(game: Game) {
        val idx = games.indexOfFirst { it.id == game.id }
        if (idx != -1 && games[idx].stock > 0) games[idx].stock--
    }

    fun nextId(): Int = nextId++
}
