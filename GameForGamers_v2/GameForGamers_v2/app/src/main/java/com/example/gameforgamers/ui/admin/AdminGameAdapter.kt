package com.example.gameforgamers.ui.admin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.databinding.ItemAdminGameBinding
import com.example.gameforgamers.model.Game

class AdminGameAdapter(
    private var items: MutableList<Game>,
    private val isEditor: Boolean,
    private val onDelete: (Game) -> Unit
    // Ya no necesitamos onIncStock ni onDecStock aquí porque se editará en la otra pantalla
) : RecyclerView.Adapter<AdminGameAdapter.VH>() {

    inner class VH(val b: ItemAdminGameBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemAdminGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val g = items[position]
        val ctx = holder.itemView.context

        // Datos visuales
        holder.b.tvTitle.text = g.title
        holder.b.tvPrice.text = g.price
        holder.b.tvStock.text = "Stock: ${g.stock}"

        val resId = ctx.resources.getIdentifier(g.drawableName, "drawable", ctx.packageName)
        if (resId != 0) holder.b.ivThumb.setImageResource(resId)

        // VISIBILIDAD DE CONTROLES (Solo para Admin 2)
        if (isEditor) {
            holder.b.layoutButtons.visibility = View.VISIBLE
        } else {
            holder.b.layoutButtons.visibility = View.GONE
        }

        // ACCIONES
        holder.b.btnDelete.setOnClickListener { onDelete(g) }

        holder.b.btnEdit.setOnClickListener {
            // Abrimos la pantalla de edición mandando el juego
            val intent = Intent(ctx, EditGameActivity::class.java)
            intent.putExtra("GAME", g)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: MutableList<Game>) {
        items = newItems
        notifyDataSetChanged()
    }
}