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

 override fun onCreateViewHolder(p: ViewGroup, v: Int): VH {
  val b = ItemGameBinding.inflate(LayoutInflater.from(p.context), p, false)
  return VH(b)
 }

 override fun onBindViewHolder(h: VH, pos: Int) {
  val g = items[pos]
  val ctx = h.b.root.context

  val id = ctx.resources.getIdentifier(g.drawableName, "drawable", ctx.packageName)
  if (id != 0) h.b.ivGame.setImageResource(id)

  h.b.tvTitle.text = g.title

  if (g.isOffer()) {
   val orig = SpannableString(g.originalPrice)
   orig.setSpan(StrikethroughSpan(), 0, orig.length, 0)
   h.b.tvPrice.visibility = View.VISIBLE
   h.b.tvPrice.text = orig

   val offer = g.offerPrice() ?: g.price
   h.b.tvOfferPrice.visibility = View.VISIBLE
   h.b.tvOfferPrice.text = "$offer  (-${g.discountPercent}%)"
  } else {
   h.b.tvPrice.visibility = View.VISIBLE
   h.b.tvPrice.text = g.price
   h.b.tvOfferPrice.visibility = View.GONE
  }

  // 🔹 Botón "Añadir al carrito" desde el catálogo/ofertas
  h.b.btnAdd.setOnClickListener {
   CartManager.add(g)
   Toast.makeText(
    ctx,
    "Se añadió \"${g.title}\" al carrito",
    Toast.LENGTH_SHORT
   ).show()
  }

  // 🔹 Click en la tarjeta → abrir detalle
  h.b.root.setOnClickListener { onClick(g) }
 }

 override fun getItemCount(): Int = items.size
}