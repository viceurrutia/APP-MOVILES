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

        // Variables de control
        val isEditor = (role == "EDITOR")   // Admin 2
        val isSupport = (role == "SUPPORT") // Admin 3 (Nuevo)

        // 2. Configuramos la interfaz según el rol
        when (role) {
            "EDITOR" -> {
                // Admin 2: Solo edita. No ve "Agregar" ni "Ingresos".
                b.tvAdminTitle.text = "Gestión de Stock (Editor)"
                b.btnAddGame.visibility = View.GONE
                b.btnIngresos.visibility = View.GONE
            }
            "SUPPORT" -> {
                // 🆕 Admin 3: Soporte. Ve "Ingresos" pero NO "Agregar".
                b.tvAdminTitle.text = "Panel de Soporte (Solo Lectura)"
                b.btnAddGame.visibility = View.GONE    // No puede crear
                b.btnIngresos.visibility = View.VISIBLE // Sí puede ver ventas
            }
            else -> {
                // Admin 1 (Owner): Ve todo.
                b.tvAdminTitle.text = "Panel de Dueño"
                b.btnAddGame.visibility = View.VISIBLE
                b.btnIngresos.visibility = View.VISIBLE
            }
        }

        // 3. Configuramos el Adapter
        // IMPORTANTE: 'isEditor' solo es true si es el Admin 2.
        // Si es Soporte (SUPPORT), 'isEditor' es false, por lo que NO verá botones de borrar/editar.
        adapter = AdminGameAdapter(
            mutableListOf(),
            isEditor = isEditor,
            onDelete = { game ->
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