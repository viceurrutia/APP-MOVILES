package com.example.gameforgamers.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.data1.GameBackendRepository
import com.example.gameforgamers.data1.Prefs
import com.example.gameforgamers.databinding.ActivityAdminBinding
import com.example.gameforgamers.model.Game
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    private lateinit var b: ActivityAdminBinding
    private lateinit var adapter: AdminGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(b.root)

        // 1. Averiguamos quién entró
        val roleMap = Prefs.getProfile(this, "CURRENT_ADMIN")
        val role = roleMap["role"] ?: "OWNER"
        val isEditor = (role == "EDITOR") // True si es Admin 2

        // 2. Configuramos la interfaz visual
        if (isEditor) {
            // ADMIN 2: Gestión
            b.tvAdminTitle.text = "Gestión de Stock y Eliminación"
            b.btnAddGame.visibility = View.GONE
            b.btnIngresos.visibility = View.GONE
        } else {
            // ADMIN 1: Dueño
            b.tvAdminTitle.text = "Panel de Dueño (Ventas)"
            b.btnAddGame.visibility = View.VISIBLE
            b.btnIngresos.visibility = View.VISIBLE
        }

        // 3. Configuramos el Adapter
        // IMPORTANTE: Aquí ya NO pasamos onIncStock ni onDecStock porque usamos la pantalla de Editar
        adapter = AdminGameAdapter(
            mutableListOf(),
            isEditor = isEditor,
            onDelete = { game ->
                // Solo permitimos borrar si es editor (doble chequeo de seguridad)
                if (isEditor) deleteGame(game)
            }
        )

        b.rvAdminGames.layoutManager = LinearLayoutManager(this)
        b.rvAdminGames.adapter = adapter

        // Listeners de botones
        b.btnAddGame.setOnClickListener {
            startActivity(Intent(this, AddGameActivity::class.java))
        }

        b.btnIngresos.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }

        // Cargamos datos
        loadGames()
    }

    override fun onResume() {
        super.onResume()
        loadGames()
    }

    private fun loadGames() {
        lifecycleScope.launch {
            try {
                val games: List<Game> = GameBackendRepository.getAll()
                adapter.update(games.toMutableList())
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@AdminActivity,
                    "Error al cargar juegos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun deleteGame(game: Game) {
        lifecycleScope.launch {
            try {
                GameBackendRepository.delete(game)
                Toast.makeText(
                    this@AdminActivity,
                    "Juego eliminado",
                    Toast.LENGTH_SHORT
                ).show()
                loadGames()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@AdminActivity,
                    "Error al eliminar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}