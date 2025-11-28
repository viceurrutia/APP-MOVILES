package com.example.gameforgamers.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.ui.admin.AddGameActivity
import com.example.gameforgamers.ui.admin.AdminGameAdapter
import com.example.gameforgamers.ui.admin.IncomeActivity
import com.example.gameforgamers.data1.GameBackendRepository
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

        adapter = AdminGameAdapter(
            mutableListOf(),
            onDelete = { game ->
                deleteGame(game)
            },
            onIncStock = { game ->
                changeStock(game, +1)
            },
            onDecStock = { game ->
                changeStock(game, -1)
            }
        )

        b.rvAdminGames.layoutManager = LinearLayoutManager(this)
        b.rvAdminGames.adapter = adapter

        b.btnAddGame.setOnClickListener {
            startActivity(Intent(this, AddGameActivity::class.java))
        }

        b.btnIngresos.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }

        // cargamos la lista inicial desde el backend
        loadGames()
    }

    override fun onResume() {
        super.onResume()
        // por si se añadió/actualizó algo
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
                    "Error al cargar juegos del servidor",
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
                    "Error al eliminar juego en el servidor",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun changeStock(game: Game, delta: Int) {
        lifecycleScope.launch {
            try {
                val newStock = (game.stock + delta).coerceAtLeast(0)
                val updatedGame = game.copy(stock = newStock)
                GameBackendRepository.update(updatedGame)
                Toast.makeText(
                    this@AdminActivity,
                    "Stock actualizado",
                    Toast.LENGTH_SHORT
                ).show()
                loadGames()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@AdminActivity,
                    "Error al actualizar stock en el servidor",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}