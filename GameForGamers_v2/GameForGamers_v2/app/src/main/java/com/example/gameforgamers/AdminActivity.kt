package com.example.gameforgamers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var b: ActivityAdminBinding
    private lateinit var adapter: AdminGameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(b.root)

        adapter = AdminGameAdapter(
            GameRepository.all(),
            onDelete = { game ->
                GameRepository.delete(game)
                adapter.update(GameRepository.all())
            },
            onIncStock = { game ->
                GameRepository.incStock(game)
                adapter.update(GameRepository.all())
            },
            onDecStock = { game ->
                GameRepository.decStock(game)
                adapter.update(GameRepository.all())
            }
        )

        b.rvAdminGames.layoutManager = LinearLayoutManager(this)
        b.rvAdminGames.adapter = adapter

        // Más adelante conectamos estos botones
        b.btnAddGame.setOnClickListener {
            // TODO: abrir AddGameActivity
        }

        b.btnIngresos.setOnClickListener {
            // TODO: abrir IncomeActivity
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.update(GameRepository.all())
    }
}
