package com.example.gameforgamers.ui.game

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.data1.CartManager
import com.example.gameforgamers.databinding.ItemGameBinding
import com.example.gameforgamers.model.Game

class GameAdapter(
 private val items: List<Game>,
 private val onClick: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.VH>() {

 inner class VH(val b: ItemGameBinding) : RecyclerView.ViewHolder(b.root)

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
  val b = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  return VH(b)
 }

 override fun onBindViewHolder(holder: VH, position: Int) {
  val g = items[position]
  val ctx = holder.b.root.context

  // Imagen
  val id = ctx.resources.getIdentifier(g.drawableName, "drawable", ctx.packageName)
  if (id != 0) holder.b.ivGame.setImageResource(id)

  holder.b.tvTitle.text = g.title

  // Precio / oferta
  if (g.isOffer()) {
   val orig = SpannableString(g.originalPrice)
   orig.setSpan(StrikethroughSpan(), 0, orig.length, 0)
   holder.b.tvPrice.visibility = View.VISIBLE
   holder.b.tvPrice.text = orig

   val offer = g.offerPrice() ?: g.price
   holder.b.tvOfferPrice.visibility = View.VISIBLE
   holder.b.tvOfferPrice.text = "$offer  (-${g.discountPercent}%)"
  } else {
   holder.b.tvPrice.visibility = View.VISIBLE
   holder.b.tvPrice.text = g.price
   holder.b.tvOfferPrice.visibility = View.GONE
  }

  // Botón "Añadir al carrito"
  holder.b.btnAdd.isEnabled = true
  holder.b.btnAdd.alpha = 1f
  holder.b.btnAdd.text = "Añadir al carrito"

  holder.b.btnAdd.setOnClickListener {
   CartManager.add(g)
   Toast.makeText(
    ctx,
    "Se añadió \"${g.title}\" al carrito",
    Toast.LENGTH_SHORT
   ).show()
  }

  // Click en la tarjeta → abrir detalle
  holder.b.root.setOnClickListener { onClick(g) }
 }

 override fun getItemCount(): Int = items.size
}
