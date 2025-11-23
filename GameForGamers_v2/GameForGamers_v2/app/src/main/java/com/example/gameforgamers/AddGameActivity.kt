package com.example.gameforgamers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityAddGameBinding
import com.example.gameforgamers.model.Game

class AddGameActivity : AppCompatActivity() {

    private lateinit var b: ActivityAddGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnSave.setOnClickListener {
            val title = b.etTitle.text.toString().trim()
            val price = b.etPrice.text.toString().trim()
            val drawable = b.etDrawable.text.toString().trim()
            val genre = b.etGenre.text.toString().trim()
            val hours = b.etHours.text.toString().trim().toIntOrNull() ?: 0
            val desc = b.etDesc.text.toString().trim()
            val stock = b.etStock.text.toString().trim().toIntOrNull() ?: 0

            // Validación básica
            if (title.isEmpty() || price.isEmpty() || drawable.isEmpty()) {
                Toast.makeText(
                    this,
                    "Completa al menos título, precio e imagen",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val game = Game(
                id = GameRepository.nextId(),
                title = title,
                price = price,
                drawableName = drawable,
                genre = genre,
                durationHours = hours,
                shortDesc = desc,
                // No seteamos oferta: originalPrice null y discountPercent 0
                originalPrice = null,
                discountPercent = 0,
                stock = stock
            )

            GameRepository.add(game)
            Toast.makeText(this, "Juego añadido correctamente", Toast.LENGTH_SHORT).show()
            finish() // volver a AdminActivity
        }
    }
}
