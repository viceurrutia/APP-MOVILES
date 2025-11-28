package com.example.gameforgamers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(b.root)

        val email = Prefs.getLogged(this)

        if (email == null) {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cargar perfil guardado
        val profile = Prefs.getProfile(this, email)

        b.etNombre.setText(profile["nombre"] ?: "")
        b.etApellido.setText(profile["apellido"] ?: "")
        b.etRegion.setText(profile["region"] ?: "")
        b.etComuna.setText(profile["comuna"] ?: "")
        // El RUT normalmente no se cambia, pero lo mostramos
        b.etRut.setText(profile["rut"] ?: "")
        b.etRut.isEnabled = false  // si quieres que no se edite

        b.btnSaveProfile.setOnClickListener {
            val nombre = b.etNombre.text.toString().trim()
            val apellido = b.etApellido.text.toString().trim()
            val region = b.etRegion.text.toString().trim()
            val comuna = b.etComuna.text.toString().trim()

            var valido = true

            if (nombre.isEmpty()) {
                b.etNombre.error = "Ingresa tu nombre"
                valido = false
            } else {
                b.etNombre.error = null
            }

            if (apellido.isEmpty()) {
                b.etApellido.error = "Ingresa tu apellido"
                valido = false
            } else {
                b.etApellido.error = null
            }

            if (region.isEmpty()) {
                b.etRegion.error = "Ingresa tu región"
                valido = false
            } else {
                b.etRegion.error = null
            }

            if (comuna.isEmpty()) {
                b.etComuna.error = "Ingresa tu comuna"
                valido = false
            } else {
                b.etComuna.error = null
            }

            if (!valido) {
                Toast.makeText(this, "Revisa los campos marcados", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar cambios en Prefs (mismas keys que en RegisterActivity)
            Prefs.saveProfile(
                this,
                email,
                mapOf(
                    "nombre" to nombre,
                    "apellido" to apellido,
                    "rut" to (profile["rut"] ?: ""),
                    "region" to region,
                    "comuna" to comuna
                )
            )

            Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
