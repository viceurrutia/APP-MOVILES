package com.example.gameforgamers.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

// 1. El ESTADO del formulario
data class PedidoUiState( // Renombrado para más claridad
    val nombre: String = "",
    val rut: String = "",
    val email: String = "",
    val juegoSeleccionado: String = "",

    val nombreError: String? = null,
    val rutError: String? = null,
    val emailError: String? = null,

    val comprobanteUri: Uri? = null // Para el recurso nativo
)

class PedidoViewModel : ViewModel() { // Renombrado
    private val _uiState = MutableStateFlow(PedidoUiState())
    val uiState: StateFlow<PedidoUiState> = _uiState.asStateFlow()

    // 2. Eventos
    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre, nombreError = null) }
    }
    fun onRutChange(rut: String) {
        _uiState.update { it.copy(rut = rut, rutError = null) }
    }
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }
    fun onComprobanteChange(uri: Uri?) {
        _uiState.update { it.copy(comprobanteUri = uri) }
    }

    // 3. Lógica de VALIDACIÓN (Ev. Parcial 2)
    fun enviarPedido() {
        val nombre = _uiState.value.nombre
        val rut = _uiState.value.rut
        val email = _uiState.value.email

        val tieneErrorNombre = nombre.length < 3
        val tieneErrorRut = rut.length < 8
        val tieneErrorEmail = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() // Validación real

        _uiState.update {
            it.copy(
                nombreError = if (tieneErrorNombre) "Nombre muy corto" else null,
                rutError = if (tieneErrorRut) "RUT inválido" else null,
                emailError = if (tieneErrorEmail) "Email no válido" else null
            )
        }
    }
}