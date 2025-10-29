package com.example.gameforgamers
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.databinding.ActivityMainBinding
import com.example.gameforgamers.model.Game
class MainActivity: AppCompatActivity(){
 private lateinit var b:ActivityMainBinding; private lateinit var toggle:ActionBarDrawerToggle
 override fun onCreate(savedInstanceState:Bundle?){ super.onCreate(savedInstanceState); b=ActivityMainBinding.inflate(layoutInflater); setContentView(b.root)
 setSupportActionBar(b.toolbar); toggle=ActionBarDrawerToggle(this,b.drawerLayout,b.toolbar,R.string.app_name,R.string.app_name); b.drawerLayout.addDrawerListener(toggle); toggle.syncState()
 b.navView.setNavigationItemSelectedListener{ item:MenuItem -> when(item.itemId){
  R.id.nav_home->{ b.sectionOffers.visibility=View.VISIBLE; b.sectionCatalog.visibility=View.VISIBLE }
  R.id.nav_offers->{ b.sectionOffers.visibility=View.VISIBLE; b.sectionCatalog.visibility=View.GONE }
  R.id.nav_catalog->{ b.sectionOffers.visibility=View.GONE; b.sectionCatalog.visibility=View.VISIBLE }
  R.id.nav_cart->{ startActivity(Intent(this, CartActivity::class.java)) }
  R.id.nav_logout->{ Prefs.setLogged(this,null); startActivity(Intent(this, LoginActivity::class.java)); finish() }
 }; b.drawerLayout.closeDrawer(GravityCompat.START); true }
 val offers=listOf(
  Game(201,"Hollow Knight: Silksong","$69.990","silksong","Metroidvania",30,"Secuela esperada", originalPrice="$69.990", discountPercent=20),
  Game(202,"The Witcher 3","$39.990","witcher3","RPG",80,"Aventura épica en mundo abierto", originalPrice="$39.990", discountPercent=50),
  Game(203,"Hades","$24.990","hades","Roguelike",25,"Acción con excelente narrativa", originalPrice="$24.990", discountPercent=30)
 )
 val catalog=listOf(
  Game(101,"Zelda TOTK","$59.990","zelda","Aventura",60,"Explora Hyrule con nuevas habilidades"),
  Game(102,"Elden Ring","$49.990","eldenring","RPG",100,"Mundo abierto desafiante y jefes épicos"),
  Game(103,"Super Mario Wonder","$39.990","mario","Plataformas",12,"Plataformas moderno y creativo"),
  Game(104,"Spider-Man 2","$59.990","spiderman","Acción",20,"Aventura trepidante en NY"),
  Game(105,"Cyberpunk 2077","$29.990","cyberpunk","RPG",35,"Historia profunda en Night City")
 )
 b.recyclerOffers.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false); b.recyclerOffers.adapter=GameAdapter(offers){ openDetail(it) }
 b.recyclerCatalog.layoutManager=LinearLayoutManager(this); b.recyclerCatalog.adapter=GameAdapter(catalog){ openDetail(it) }
 }
 private fun openDetail(g:Game){ val i=Intent(this,GameDetailActivity::class.java); i.putExtra("game",g); startActivity(i); overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out) }
}