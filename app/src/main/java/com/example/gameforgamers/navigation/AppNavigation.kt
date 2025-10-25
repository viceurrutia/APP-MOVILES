package com.example.gameforgamers.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.gameforgamers.viewmodel.CatalogoViewModel
import com.example.gameforgamers.viewmodel.PedidoViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    catalogoViewModel: CatalogoViewModel,
    pedidoViewModel: PedidoViewModel // ¡Actualizado!
) {
    NavHost(navController = navController, startDestination = Screen.Catalogo.route) {

        composable(Screen.Catalogo.route) {
            CatalogoScreen(viewModel = catalogoViewModel)
        }

        composable(Screen.Pedido.route) { // ¡Actualizado!
            PedidoScreen(viewModel = pedidoViewModel) // ¡Actualizado!
        }
    }
}

@Composable
fun PedidoScreen(viewModel: PedidoViewModel) {
    TODO("Not yet implemented")
}

@Composable
fun CatalogoScreen(viewModel: CatalogoViewModel) {
    TODO("Not yet implemented")
}