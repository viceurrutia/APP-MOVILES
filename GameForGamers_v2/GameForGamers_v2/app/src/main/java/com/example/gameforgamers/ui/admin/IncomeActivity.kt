package com.example.gameforgamers.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.data1.GameBackendRepository
import com.example.gameforgamers.databinding.ActivityIncomeBinding
import kotlinx.coroutines.launch

class IncomeActivity : AppCompatActivity() {

    private lateinit var b: ActivityIncomeBinding
    private lateinit var adapter: IncomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.tvIncomeTitle.text = "Historial de Pedidos"

        // Iniciamos vacío
        adapter = IncomeAdapter(emptyList())
        b.rvIncome.layoutManager = LinearLayoutManager(this)
        b.rvIncome.adapter = adapter

        // Cargamos datos reales
        loadPurchases()
    }

    private fun loadPurchases() {
        lifecycleScope.launch {
            try {
                val list = GameBackendRepository.getPurchases()

                if (list.isEmpty()) {
                    Toast.makeText(this@IncomeActivity, "Aún no hay ventas registradas", Toast.LENGTH_SHORT).show()
                }

                adapter.update(list)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@IncomeActivity, "Error al cargar ventas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}