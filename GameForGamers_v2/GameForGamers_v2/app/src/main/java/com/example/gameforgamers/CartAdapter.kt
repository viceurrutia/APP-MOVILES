package com.example.gameforgamers
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.databinding.ItemCartBinding
import com.example.gameforgamers.model.Game
class CartAdapter(private var items:MutableList<Pair<Game,Int>>, private val onAdd:(Pair<Game,Int>)->Unit, private val onDec:(Pair<Game,Int>)->Unit, private val onRemove:(Pair<Game,Int>)->Unit): RecyclerView.Adapter<CartAdapter.VH>(){
 inner class VH(val b:ItemCartBinding): RecyclerView.ViewHolder(b.root)
 override fun onCreateViewHolder(p:ViewGroup,v:Int):VH{ val b=ItemCartBinding.inflate(LayoutInflater.from(p.context),p,false); return VH(b) }
 override fun onBindViewHolder(h:VH,pos:Int){ val (g,q)=items[pos]; val ctx=h.b.root.context; val id=ctx.resources.getIdentifier(g.drawableName,"drawable",ctx.packageName); if(id!=0) h.b.ivThumb.setImageResource(id); h.b.tvTitle.text=g.title; h.b.tvPrice.text=(g.offerPrice()?:g.price)+" x "+q; h.b.btnAdd.setOnClickListener{ onAdd(Pair(g,q)) }; h.b.btnDec.setOnClickListener{ onDec(Pair(g,q)) }; h.b.btnRemove.setOnClickListener{ onRemove(Pair(g,q)) } }
 override fun getItemCount():Int=items.size
 fun update(n:MutableList<Pair<Game,Int>>){ items=n; notifyDataSetChanged() }
}