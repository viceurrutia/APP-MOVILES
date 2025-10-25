package com.example.gameforgamers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CatalogoViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = DataRepository(app)

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val juegos: StateFlow<List<Juego>> = repository.getJuegos() // ¡Actualizado!
        .onStart { _isLoading.value = true }
        .onEach { _isLoading.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.checkAndPrepopulateDatabase()
        }
    }
}