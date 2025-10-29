package com.example.gameforgamers
import com.example.gameforgamers.model.Game
object CartManager{
 private val items=mutableMapOf<Int,Pair<Game,Int>>()
 fun add(g:Game){ val q=items[g.id]?.second?:0; items[g.id]=Pair(g,q+1) }
 fun dec(g:Game){ val q=items[g.id]?.second?:0; if(q<=1) items.remove(g.id) else items[g.id]=Pair(g,q-1) }
 fun remove(g:Game){ items.remove(g.id) }
 fun all():List<Pair<Game,Int>> = items.values.toList()
 fun clear(){ items.clear() }
 fun total():Int{ var t=0; for((g,q) in items.values){ val v=(g.offerPrice() ?: g.price).replace("$","").replace(".",""); t+=(v.toIntOrNull()?:0)*q }; return t }
}