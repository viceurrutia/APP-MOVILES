package com.example.gameforgamers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gameforgamers.databinding.ActivityAddGameBinding
import com.example.gameforgamers.model.Game
import kotlinx.coroutines.launch

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

            // id = 0 porque el backend genera el id real
            val game = Game(
                id = 0,
                title = title,
                price = price,
                drawableName = drawable,
                genre = genre,
                durationHours = hours,
                shortDesc = desc,
                originalPrice = null,
                discountPercent = 0,
                stock = stock
            )

            lifecycleScope.launch {
                try {
                    val saved = GameBackendRepository.create(game)

                    // (opcional) mantener el repo local sincronizado
                    GameRepository.add(saved)

                    Toast.makeText(
                        this@AddGameActivity,
                        "Juego añadido en el servidor (id=${saved.id})",
                        Toast.LENGTH_SHORT
                    ).show()

                    setResult(RESULT_OK)
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@AddGameActivity,
                        "Error al guardar juego en el servidor",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
