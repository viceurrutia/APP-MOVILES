package com.example.gameforgamers.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gameforgamers.viewmodel.PedidoViewModel

@Composable
fun PedidoScreen(viewModel: PedidoViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    // --- RECURSOS NATIVOS OBLIGATORIOS (PDF 2.4.3 y Ev. Parcial 2) ---
    // Para adjuntar "comprobante de pago"

    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> viewModel.onComprobanteChange(uri) }
    )
    // val launcherCamara = ... (CÁMARA)

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Text("Realizar Pedido", style = MaterialTheme.typography.headlineMedium) }

        item {
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                isError = uiState.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = uiState.nombreError != null) {
                Text(uiState.nombreError ?: "", color = MaterialTheme.colorScheme.error)
            }
        }

        item {
            OutlinedTextField(
                value = uiState.rut,
                onValueChange = { viewModel.onRutChange(it) },
                label = { Text("RUT") },
                isError = uiState.rutError != null,
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = uiState.rutError != null) {
                Text(uiState.rutError ?: "", color = MaterialTheme.colorScheme.error)
            }
        }

        item {
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email de contacto") },
                isError = uiState.emailError != null,
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = uiState.emailError != null) {
                Text(uiState.emailError ?: "", color = MaterialTheme.colorScheme.error)
            }
        }

        item {
            Button(onClick = { launcherGaleria.launch("image/*") }) {
                Text("Adjuntar Comprobante (Galería)")
            }
        }
        if (uiState.comprobanteUri != null) {
            item { Text("Comprobante adjuntado.") }
        }

        item {
            Button(
                onClick = { viewModel.enviarPedido() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Pedido")
            }
        }
    }
}