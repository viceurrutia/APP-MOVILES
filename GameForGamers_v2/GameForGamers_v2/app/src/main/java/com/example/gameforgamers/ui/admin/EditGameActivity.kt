package com.example.gameforgamers.ui.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gameforgamers.data1.GameBackendRepository
import com.example.gameforgamers.databinding.ActivityEditGameBinding
import com.example.gameforgamers.model.Game
import kotlinx.coroutines.launch

class EditGameActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditGameBinding
    private var currentGame: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditGameBinding.inflate(layoutInflater)
        setContentView(b.root)

        // 1. Recibimos el juego que vamos a editar
        currentGame = intent.getSerializableExtra("GAME") as? Game

        if (currentGame == null) {
            Toast.makeText(this, "Error al cargar juego", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2. Rellenamos los campos con los datos actuales
        val g = currentGame!!
        b.etTitle.setText(g.title)
        b.etPrice.setText(g.price)
        b.etStock.setText(g.stock.toString())
        b.etGenre.setText(g.genre)
        b.etDrawable.setText(g.drawableName)
        b.etDesc.setText(g.shortDesc)

        // 3. Guardar cambios
        b.btnSaveChanges.setOnClickListener {
            saveChanges()
        }
    }

    private fun saveChanges() {
        val g = currentGame ?: return

        // Creamos un juego nuevo con los datos de las cajas de texto
        // PERO MANTENIENDO EL MISMO ID (Importante para que sea Update y no Create)
        val updatedGame = g.copy(
            title = b.etTitle.text.toString(),
            price = b.etPrice.text.toString(),
            stock = b.etStock.text.toString().toIntOrNull() ?: 0,
            genre = b.etGenre.text.toString(),
            drawableName = b.etDrawable.text.toString(),
            shortDesc = b.etDesc.text.toString()
        )

        lifecycleScope.launch {
            try {
                // Llamamos al Backend para actualizar (PUT)
                GameBackendRepository.update(updatedGame)

                Toast.makeText(this@EditGameActivity, "Juego actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish() // Cierra y vuelve a la lista
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EditGameActivity, "Error al guardar cambios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}