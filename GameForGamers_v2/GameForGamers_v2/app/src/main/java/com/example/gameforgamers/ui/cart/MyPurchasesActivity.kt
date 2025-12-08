package com.example.gameforgamers.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.data1.GameBackendRepository
import com.example.gameforgamers.data1.Prefs
import com.example.gameforgamers.databinding.ActivityIncomeBinding // Reusamos XML de ingresos
import com.example.gameforgamers.ui.admin.IncomeAdapter // Reusamos Adapter
import kotlinx.coroutines.launch

class MyPurchasesActivity : AppCompatActivity() {

    private lateinit var b: ActivityIncomeBinding
    private lateinit var adapter: IncomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos el mismo diseño que Ingresos
        b = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.tvIncomeTitle.text = "Mis Compras Realizadas"

        adapter = IncomeAdapter(emptyList())
        b.rvIncome.layoutManager = LinearLayoutManager(this)
        b.rvIncome.adapter = adapter

        loadMyHistory()
    }

    private fun loadMyHistory() {
        val email = Prefs.getLogged(this) ?: return

        lifecycleScope.launch {
            try {
                // 👇 Aquí pedimos SOLO las compras de este email
                val mylist = GameBackendRepository.getMyPurchases(email)

                if (mylist.isEmpty()) {
                    Toast.makeText(this@MyPurchasesActivity, "Aún no tienes compras", Toast.LENGTH_SHORT).show()
                }
                adapter.update(mylist)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MyPurchasesActivity, "Error al cargar historial", Toast.LENGTH_SHORT).show()
            }
        }
    }
}