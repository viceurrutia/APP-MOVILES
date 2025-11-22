package com.example.gameforgamers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.databinding.ItemAdminGameBinding
import com.example.gameforgamers.model.Game

class AdminGameAdapter(
    private var items: MutableList<Game>,
    private val onDelete: (Game) -> Unit,
    private val onIncStock: (Game) -> Unit,
    private val onDecStock: (Game) -> Unit
) : RecyclerView.Adapter<AdminGameAdapter.VH>() {

    inner class VH(val b: ItemAdminGameBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemAdminGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val g = items[position]
        val ctx = holder.b.root.context

        val resId = ctx.resources.getIdentifier(g.drawableName, "drawable", ctx.packageName)
        if (resId != 0) {
            holder.b.ivThumb.setImageResource(resId)
        }

        holder.b.tvTitle.text = g.title

        val offer = g.offerPrice()
        if (offer != null) {
            holder.b.tvPrice.text = offer
        } else {
            holder.b.tvPrice.text = g.price
        }

        holder.b.tvStock.text = "Stock: ${g.stock}"

        holder.b.btnPlus.setOnClickListener { onIncStock(g) }
        holder.b.btnMinus.setOnClickListener { onDecStock(g) }
        holder.b.btnDelete.setOnClickListener { onDelete(g) }
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: MutableList<Game>) {
        items = newItems
        notifyDataSetChanged()
    }
}
