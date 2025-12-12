package com.example.gameforgamers.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.data1.GameBackendRepository
import com.example.gameforgamers.databinding.ActivityIncomeBinding // Reusamos el diseño de ingresos
import kotlinx.coroutines.launch

class UserListActivity : AppCompatActivity() {

    private lateinit var b: ActivityIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos activity_income.xml porque ya tiene un RecyclerView y un Título
        b = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.tvIncomeTitle.text = "Usuarios Registrados"

        loadUsers()
    }

    private fun loadUsers() {
        lifecycleScope.launch {
            try {
                // Pedimos la lista al Backend
                val users = GameBackendRepository.getUsers()

                if (users.isEmpty()) {
                    Toast.makeText(this@UserListActivity, "No hay usuarios registrados", Toast.LENGTH_SHORT).show()
                }

                // Configuramos la lista
                b.rvIncome.layoutManager = LinearLayoutManager(this@UserListActivity)
                b.rvIncome.adapter = SimpleUserAdapter(users)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@UserListActivity, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}