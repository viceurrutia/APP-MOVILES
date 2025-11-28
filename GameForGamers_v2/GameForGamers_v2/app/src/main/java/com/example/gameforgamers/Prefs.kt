package com.example.gameforgamers

import android.content.Context
import java.security.MessageDigest

object Prefs {

 // Cambié el nombre para "resetear" las prefs (así no chocan las contraseñas viejas en texto plano)
 private const val PREFS = "gfg_prefs_v2"

 private const val KEY_USERS = "users"
 private const val KEY_LOGGED = "logged_email"

 private fun p(ctx: Context) = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

 // --- HASH de contraseña (SHA-256) ---
 private fun hashPassword(pass: String): String {
  val md = MessageDigest.getInstance("SHA-256")
  val bytes = md.digest(pass.toByteArray())
  return bytes.joinToString("") { "%02x".format(it) }   // hex
 }

 // Solo para cargar usuarios de ejemplo (si quieres)
 fun ensureSeed(ctx: Context) {
  val u = p(ctx).getString(KEY_USERS, "") ?: ""
  if (u.isEmpty()) {
   val h1 = hashPassword("1234")
   val h2 = hashPassword("abcd")
   val seed = "user1@mail.com|$h1;user2@mail.com|$h2;"
   p(ctx).edit().putString(KEY_USERS, seed).apply()
  }
 }

 // Guarda usuario NUEVO (password se guarda hasheada)
 fun saveUser(ctx: Context, email: String, pass: String) {
  val u = p(ctx).getString(KEY_USERS, "") ?: ""
  if (!u.contains("$email|")) {
   val hashed = hashPassword(pass)
   p(ctx).edit().putString(KEY_USERS, u + "$email|$hashed;").apply()
  }
 }

 // Verifica login (hashea lo que escribe el usuario y compara)
 fun check(ctx: Context, email: String, pass: String): Boolean {
  val u = p(ctx).getString(KEY_USERS, "") ?: ""
  val hashedInput = hashPassword(pass)
  return u.split(";")
   .filter { it.isNotBlank() }
   .any {
    val s = it.split("|")
    s.size == 2 && s[0] == email && s[1] == hashedInput
   }
 }

 fun setLogged(ctx: Context, email: String?) {
  p(ctx).edit().putString(KEY_LOGGED, email).apply()
 }

 fun getLogged(ctx: Context): String? = p(ctx).getString(KEY_LOGGED, null)

 // --- Cambiar contraseña existente (para "Olvidé mi contraseña") ---
 fun updatePassword(ctx: Context, email: String, newPass: String): Boolean {
  val u = p(ctx).getString(KEY_USERS, "") ?: ""
  if (u.isEmpty()) return false

  val parts = u.split(";")
   .filter { it.isNotBlank() }
   .map { it.split("|") }
   .toMutableList()

  var found = false
  val hashed = hashPassword(newPass)

  for (i in parts.indices) {
   if (parts[i].size == 2 && parts[i][0] == email) {
    parts[i] = listOf(email, hashed)
    found = true
    break
   }
  }

  if (!found) return false

  val newFlat = parts.joinToString(";") { "${it[0]}|${it[1]}" } + ";"
  p(ctx).edit().putString(KEY_USERS, newFlat).apply()
  return true
 }

 // --- Perfil (igual que tenías) ---
 fun saveProfile(ctx: Context, email: String, map: Map<String, String>) {
  val key = "profile_$email"
  val flat = map.entries.joinToString("&") { "${it.key}=${it.value.replace("&", " ")}" }
  p(ctx).edit().putString(key, flat).apply()
 }

 fun getProfile(ctx: Context, email: String): Map<String, String> {
  val key = "profile_$email"
  val raw = p(ctx).getString(key, "") ?: return emptyMap()
  if (raw.isEmpty()) return emptyMap()
  return raw.split("&").mapNotNull {
   val parts = it.split("=")
   if (parts.size == 2) parts[0] to parts[1] else null
  }.toMap()
 }
}
