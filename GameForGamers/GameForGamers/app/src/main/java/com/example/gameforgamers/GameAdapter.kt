package com.example.gameforgamers
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.databinding.ItemGameBinding
import com.example.gameforgamers.model.Game
class GameAdapter(private val items:List<Game>, private val onClick:(Game)->Unit): RecyclerView.Adapter<GameAdapter.VH>(){
 inner class VH(val b:ItemGameBinding): RecyclerView.ViewHolder(b.root)
 override fun onCreateViewHolder(p:ViewGroup,v:Int):VH{ val b=ItemGameBinding.inflate(LayoutInflater.from(p.context),p,false); return VH(b) }
 override fun onBindViewHolder(h:VH,pos:Int){ val g=items[pos]; val ctx=h.b.root.context; val id=ctx.resources.getIdentifier(g.drawableName,"drawable",ctx.packageName); if(id!=0) h.b.ivGame.setImageResource(id); h.b.tvTitle.text=g.title; h.b.tvPrice.text=g.price; h.b.btnAdd.setOnClickListener{ CartManager.add(g) }; h.b.root.setOnClickListener{ onClick(g) } }
 override fun getItemCount():Int=items.size
}
