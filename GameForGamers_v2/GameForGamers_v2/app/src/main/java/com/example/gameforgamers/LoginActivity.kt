package com.example.gameforgamers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

   // 1) ADMIN FIJO
   if (email == "admin@admin.com" && pass == "admin123") {
    startActivity(Intent(this, AdminActivity::class.java))
    finish()
    return@setOnClickListener
   }

   // 2) USUARIO NORMAL (usa Prefs como antes)
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
 }
}
