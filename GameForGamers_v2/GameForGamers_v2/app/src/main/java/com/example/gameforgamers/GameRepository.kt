package com.example.gameforgamers

import com.example.gameforgamers.model.Game

object GameRepository {

    // Todos los juegos iniciales (catálogo + ofertas)
    private val games = mutableListOf(
        // Catálogo
        Game(
            101,
            "Zelda TOTK",
            "$39.990",
            "zelda",
            "Aventura",
            60,
            "Explora Hyrule con nuevas habilidades",
            stock = 10
        ),
        Game(
            102,
            "Elden Ring",
            "$49.990",
            "eldenring",
            "RPG",
            100,
            "Mundo abierto desafiante y jefes épicos",
            stock = 10
        ),
        Game(
            103,
            "Super Mario Wonder",
            "$39.990",
            "mario",
            "Plataformas",
            12,
            "Plataformas moderno y creativo",
            stock = 20
        ),
        Game(
            104,
            "Spider-Man 2",
            "$59.990",
            "spiderman",
            "Acción",
            20,
            "Aventura trepidante en NY",
            stock = 10
        ),
        Game(
            105,
            "Cyberpunk 2077",
            "$29.990",
            "cyberpunk",
            "RPG",
            35,
            "Historia profunda en Night City",
            stock = 15
        ),

        // Ofertas del momento
        Game(
            201,
            "Hollow Knight: Silksong",
            "$19.990",
            "silksong",
            "Metroidvania",
            30,
            "Secuela esperada",
            originalPrice = "$19.990",
            discountPercent = 30,
            stock = 10
        ),
        Game(
            202,
            "The Witcher 3",
            "$39.990",
            "witcher3",
            "RPG",
            80,
            "Aventura épica en mundo abierto",
            originalPrice = "$39.990",
            discountPercent = 50,
            stock = 10
        ),
        Game(
            203,
            "Resident Evil 4 Remake",
            "$19.990",
            "re4", // 👈 nombre del drawable
            "Survival Horror",
            25,
            "Remake del clásico de acción y terror",
            originalPrice = "$19.990",
            discountPercent = 15,
            stock = 10
        )

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
