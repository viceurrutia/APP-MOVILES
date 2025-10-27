package com.example.tiendavideojuegos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendavideojuegos.databinding.ItemGameBinding

class GameAdapter(private val games: List<Game>, private val onClick: (Game) -> Unit) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    inner class GameViewHolder(val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.binding.tvTitle.text = game.title
        holder.binding.tvPrice.text = String.format("$%.2f", game.price)

        val resId = holder.binding.root.context.resources.getIdentifier(
            game.imageResName, "drawable", holder.binding.root.context.packageName
        )
        if (resId != 0) holder.binding.ivGame.setImageResource(resId)

        holder.binding.root.setOnClickListener {
            onClick(game)
        }
    }

    override fun getItemCount() = games.size
}
