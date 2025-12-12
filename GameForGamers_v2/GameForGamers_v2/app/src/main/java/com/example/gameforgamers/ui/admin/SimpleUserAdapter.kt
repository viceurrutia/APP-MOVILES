package com.example.gameforgamers.ui.admin

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.model.AppUser

class SimpleUserAdapter(private val users: List<AppUser>) : RecyclerView.Adapter<SimpleUserAdapter.VH>() {

    class VH(val tv: TextView) : RecyclerView.ViewHolder(tv)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        // Creamos un TextView simple por código para no crear otro XML
        val tv = TextView(parent.context)
        tv.setPadding(40, 40, 40, 40) // Espacio interno
        tv.textSize = 16f
        return VH(tv)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val u = users[position]
        // Así se verá cada fila:
        holder.tv.text = "👤 ${u.name}\n📧 ${u.email}\n🔑 Rol: ${u.role}"
    }

    override fun getItemCount() = users.size
}