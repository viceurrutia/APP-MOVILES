package com.example.gameforgamers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.databinding.ActivityMainBinding
import com.example.gameforgamers.model.Game
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

 private lateinit var b: ActivityMainBinding
 private lateinit var toggle: ActionBarDrawerToggle

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  b = ActivityMainBinding.inflate(layoutInflater)
  setContentView(b.root)

  // Toolbar + Drawer
  setSupportActionBar(b.toolbar)
  toggle = ActionBarDrawerToggle(
   this,
   b.drawerLayout,
   b.toolbar,
   R.string.app_name,
   R.string.app_name
  )
  b.drawerLayout.addDrawerListener(toggle)
  toggle.syncState()

  // Menú lateral
  b.navView.setNavigationItemSelectedListener { item: MenuItem ->
   when (item.itemId) {
    R.id.nav_home -> {
     b.sectionOffers.visibility = View.VISIBLE
     b.sectionCatalog.visibility = View.VISIBLE
    }
    R.id.nav_offers -> {
     b.sectionOffers.visibility = View.VISIBLE
     b.sectionCatalog.visibility = View.GONE
    }
    R.id.nav_catalog -> {
     b.sectionOffers.visibility = View.GONE
     b.sectionCatalog.visibility = View.VISIBLE
    }
    R.id.nav_cart -> {
     startActivity(Intent(this, CartActivity::class.java))
    }
    R.id.nav_logout -> {
     Prefs.setLogged(this, null)
     startActivity(Intent(this, LoginActivity::class.java))
     finish()
    }
   }
   b.drawerLayout.closeDrawer(GravityCompat.START)
   true
  }

  // LayoutManagers de los RecyclerView
  b.recyclerOffers.layoutManager =
   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
  b.recyclerCatalog.layoutManager = LinearLayoutManager(this)

  // Cargar datos iniciales
  loadGames()
  loadWeather()
 }

 override fun onResume() {
  super.onResume()
  // Por si el admin agregó juegos nuevos
  loadGames()
  loadWeather()
 }

 private fun loadGames() {
  val allGames: List<Game> = GameRepository.all()

  // Ofertas: solo los que tienen oferta
  val offers = allGames.filter { it.isOffer() }

  // Catálogo: todos los que NO son oferta
  val catalog = allGames.filter { !it.isOffer() }

  b.recyclerOffers.adapter = GameAdapter(offers) { openDetail(it) }
  b.recyclerCatalog.adapter = GameAdapter(catalog) { openDetail(it) }
 }

 private fun loadWeather() {
  lifecycleScope.launch {
   val temp = WeatherRepository.getCurrentTempCelsius()
   val cityLabel = "Santiago"

   if (temp != null) {
    val t = temp.toInt()
    b.tvWeather.text = "$cityLabel 🌤 ${t}°C"
   } else {
    b.tvWeather.text = "$cityLabel 🌥 --°C"
   }
  }
 }


 private fun openDetail(g: Game) {
  val i = Intent(this, GameDetailActivity::class.java)
  i.putExtra("game", g)
  startActivity(i)
  overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out)
 }
}
