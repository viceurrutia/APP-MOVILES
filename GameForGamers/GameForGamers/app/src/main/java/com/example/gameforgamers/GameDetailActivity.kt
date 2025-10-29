package com.example.gameforgamers
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityGameDetailBinding
import com.example.gameforgamers.model.Game
class GameDetailActivity: AppCompatActivity(){
 private lateinit var b:ActivityGameDetailBinding
 override fun onCreate(savedInstanceState:Bundle?){ super.onCreate(savedInstanceState); b=ActivityGameDetailBinding.inflate(layoutInflater); setContentView(b.root)
 val g=intent.getSerializableExtra("game") as? Game ?: return
 val id=resources.getIdentifier(g.drawableName,"drawable",packageName); if(id!=0) b.ivGame.setImageResource(id)
 b.tvTitle.text=g.title; b.tvPrice.text=g.price; b.tvDesc.text=g.description
 b.btnAdd.setOnClickListener{ CartManager.add(g); Toast.makeText(this,"Añadido al carrito",Toast.LENGTH_SHORT).show() }
 }
}
