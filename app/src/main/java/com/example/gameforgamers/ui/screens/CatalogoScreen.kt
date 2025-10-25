package com.example.gameforgamers.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gameforgamers.data.Juego
import com.example.gameforgamers.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(viewModel: CatalogoViewModel) {
    val juegos by viewModel.juegos.collectAsState() // ¡Actualizado!
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(juegos) { juego -> // ¡Actualizado!
                JuegoCard(juego = juego) // ¡Actualizado!
            }
        }
    }
}

@Composable
fun JuegoCard(juego: Juego) { // ¡Actualizado!
    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        Column {
            AsyncImage(
                model = juego.imagenUrl,
                contentDescription = juego.nombre,
                modifier = Modifier.fillMaxWidth().aspectRatio(0.75f), // Aspecto de portada
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = juego.nombre, fontWeight = FontWeight.Bold)
                Text(text = juego.plataforma, style = MaterialTheme.typography.bodySmall) // Plataforma
                Text(text = "$ ${juego.precio.toInt()}")
            }
        }
    }
}