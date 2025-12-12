package com.example.gameforgamers.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.ui.admin.AdminActivity
import com.example.gameforgamers.ui.main.MainActivity
import com.example.gameforgamers.data1.Prefs
import com.example.gameforgamers.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Carga usuarios de prueba si no existen
        Prefs.ensureSeed(this)

        // Si ya hay alguien logeado, lo manda directo al Main
        Prefs.getLogged(this)?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // BOTÓN INICIAR SESIÓN
        b.btnLogin.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1) ADMIN 1: DUEÑO (Ve ingresos y agrega juegos)
            if (email == "admin@admin.com" && pass == "admin123") {
                // Guardamos que es el DUEÑO (OWNER)
                Prefs.saveProfile(this, "CURRENT_ADMIN", mapOf("role" to "OWNER"))

                startActivity(Intent(this, AdminActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // 2) ADMIN 2: EDITOR (Edita stock y elimina juegos)
            if (email == "admin2@admin.com" && pass == "admin1234") {
                // Guardamos que es el EDITOR
                Prefs.saveProfile(this, "CURRENT_ADMIN", mapOf("role" to "EDITOR"))

                startActivity(Intent(this, AdminActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // 3) 🆕 NUEVO ROL: SOPORTE (Solo lectura + Ingresos)
            if (email == "soporte@admin.cl" && pass == "soporte123") {
                Prefs.saveProfile(this, "CURRENT_ADMIN", mapOf("role" to "SUPPORT"))
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
                return@setOnClickListener
            }

            // 4) USUARIO NORMAL (Cliente)
            if (Prefs.check(this, email, pass)) {
                Prefs.setLogged(this, email)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }

        // BOTÓN IR A REGISTRO
        b.btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // BOTÓN ¿OLVIDASTE TU CONTRASEÑA?
        b.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}