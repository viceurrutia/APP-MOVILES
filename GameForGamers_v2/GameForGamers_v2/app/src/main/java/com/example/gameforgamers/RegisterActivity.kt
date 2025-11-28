package com.example.gameforgamers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

 private lateinit var b: ActivityRegisterBinding

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  b = ActivityRegisterBinding.inflate(layoutInflater)
  setContentView(b.root)

  b.btnRegister.setOnClickListener {

   val nombre = b.etNombre.text.toString().trim()
   val apellido = b.etApellido.text.toString().trim()
   val rut = b.etRut.text.toString().trim()
   val region = b.etRegion.text.toString().trim()
   val comuna = b.etComuna.text.toString().trim()
   val email = b.etEmail.text.toString().trim()
   val pass = b.etPassword.text.toString()

   var valido = true

   // 🔹 Validar nombre
   if (!validarNombre(nombre)) {
    b.etNombre.error = "Nombre inválido. Use solo letras."
    valido = false
   } else {
    b.etNombre.error = null
   }

   // 🔹 Validar apellido
   if (!validarApellido(apellido)) {
    b.etApellido.error = "Apellido inválido. Use solo letras."
    valido = false
   } else {
    b.etApellido.error = null
   }

   // 🔹 Validar RUT
   if (rut.isEmpty()) {
    b.etRut.error = "Ingresa tu RUT"
    valido = false
   } else if (!isValidRut(rut)) {
    b.etRut.error = "RUT inválido"
    valido = false
   } else {
    b.etRut.error = null
   }

   // 🔹 Región
   if (region.isEmpty()) {
    b.etRegion.error = "Ingresa tu región"
    valido = false
   } else {
    b.etRegion.error = null
   }

   // 🔹 Comuna
   if (comuna.isEmpty()) {
    b.etComuna.error = "Ingresa tu comuna"
    valido = false
   } else {
    b.etComuna.error = null
   }

   // 🔹 Email
   val emailRx = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
   if (email.isEmpty()) {
    b.etEmail.error = "Ingresa tu correo"
    valido = false
   } else if (!emailRx.matches(email)) {
    b.etEmail.error = "Correo inválido"
    valido = false
   } else {
    b.etEmail.error = null
   }

   // 🔹 Contraseña
   if (pass.length < 6) {
    b.etPassword.error = "Contraseña mínima de 6 caracteres"
    valido = false
   } else {
    b.etPassword.error = null
   }

   // Si algo está malo, no seguimos
   if (!valido) {
    Toast.makeText(this, "Revisa los campos marcados", Toast.LENGTH_SHORT).show()
    return@setOnClickListener
   }

   // ✅ Si todo ok: guardar usuario y perfil
   Prefs.saveUser(this, email, pass) // se guarda con hash por dentro
   Prefs.saveProfile(
    this,
    email,
    mapOf(
     "nombre" to nombre,
     "apellido" to apellido,
     "rut" to rut,
     "region" to region,
     "comuna" to comuna
    )
   )

   Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
   finish() // volver al login
  }
 }

 private fun isValidRut(rut: String): Boolean {
  val clean = rut.replace(".", "").replace("-", "").uppercase()
  if (clean.length < 8) return false
  val body = clean.dropLast(1)
  val dv = clean.last()

  var sum = 0
  var mult = 2

  for (c in body.reversed()) {
   sum += (c.digitToInt()) * mult
   mult = if (mult == 7) 2 else mult + 1
  }

  val res = 11 - (sum % 11)
  val dvCalc = when (res) {
   11 -> '0'
   10 -> 'K'
   else -> ('0' + res)
  }

  return dv == dvCalc
 }

 private fun validarNombre(nombre: String): Boolean {
  if (nombre.isBlank()) return false
  if (nombre.length < 2) return false
  val regex = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")
  return regex.matches(nombre.trim())
 }

 private fun validarApellido(apellido: String): Boolean {
  if (apellido.isBlank()) return false
  if (apellido.length < 2) return false
  val regex = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")
  return regex.matches(apellido.trim())
 }
}
