package com.example.gameforgamers.navigation

sealed class Screen(val route: String) {
    object Catalogo : Screen("catalogo")
    object Pedido : Screen("pedido") // Renombrado
    // Añade Login, etc. aquí
}