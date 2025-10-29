package com.example.gameforgamers
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityGameDetailBinding
import com.example.gameforgamers.model.Game
class GameDetailActivity: AppCompatActivity(){
 private lateinit var b:ActivityGameDetailBinding
 override fun onCreate(savedInstanceState:Bundle?){ super.onCreate(savedInstanceState); b=ActivityGameDetailBinding.inflate(layoutInflater); setContentView(b.root)
 val g=intent.getSerializableExtra("game") as? Game ?: return
 val id=resources.getIdentifier(g.drawableName,"drawable",packageName); if(id!=0) b.ivGame.setImageResource(id)
 b.tvTitle.text=g.title; b.tvGenre.text="Género: ${g.genre}"; b.tvDuration.text="Duración estimada: ${g.durationHours} h"; b.tvDesc.text=g.shortDesc
 if(g.isOffer()){ val orig=SpannableString(g.originalPrice); orig.setSpan(StrikethroughSpan(),0,orig.length,0); b.tvPrice.text=orig; b.tvOffer.visibility=View.VISIBLE; b.tvOffer.text="${g.offerPrice()}  (-${g.discountPercent}%)" } else { b.tvPrice.text=g.price; b.tvOffer.visibility=View.GONE }
 b.btnAdd.setOnClickListener{ CartManager.add(g); Toast.makeText(this,"Añadido al carrito",Toast.LENGTH_SHORT).show() }
 }
}