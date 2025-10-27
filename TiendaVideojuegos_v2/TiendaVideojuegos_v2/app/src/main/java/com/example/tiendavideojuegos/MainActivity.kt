package com.example.tiendavideojuegos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendavideojuegos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            Prefs.setLogged(this, null)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val games = listOf(
            Game("The Legend of Zelda: Tears of the Kingdom", 59.99, "zelda", "Explora Hyrule y descubre secretos."),
            Game("Elden Ring", 49.99, "eldenring", "Un mundo abierto desafiante creado con FromSoftware."),
            Game("Super Mario Bros Wonder", 59.99, "mario", "Aventura clásica de Mario con nuevas mecánicas.")
        )

        adapter = GameAdapter(games) { game ->
            val intent = Intent(this, GameDetailActivity::class.java)
            intent.putExtra("title", game.title)
            intent.putExtra("price", game.price)
            intent.putExtra("image", game.imageResName)
            intent.putExtra("description", game.description)
            startActivity(intent)
        }

        binding.recyclerViewGames.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewGames.adapter = adapter
    }
}
