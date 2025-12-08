package com.example.gameforgamers.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.databinding.ItemIncomeBinding
import com.example.gameforgamers.model.Purchase

class IncomeAdapter(
    private var items: List<Purchase>
) : RecyclerView.Adapter<IncomeAdapter.VH>() {

    inner class VH(val b: ItemIncomeBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemIncomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        val ctx = holder.itemView.context

        // 1. Orden ID
        holder.b.tvOrderId.text = "Orden #${p.id}"

        // 2. Resumen de juegos (CORREGIDO: Usamos ?: "" para evitar NullPointerException)
        val info = p.itemsInfo ?: ""
        holder.b.tvItems.text = info.ifEmpty { "Sin detalle" }

        // 3. Fecha y Email (CORREGIDO: blindaje contra nulos)
        holder.b.tvDate.text = "${p.date ?: "--"} | ${p.customerEmail ?: "Sin email"}"

        // 4. Monto
        holder.b.tvAmount.text = "$" + "%,d".format(p.totalAmount).replace(",", ".")

        // 5. Imagen (CORREGIDO: Verificamos antes de buscar)
        val imgName = p.imageCode ?: "" // Si es null, usa vacío
        var resourceId = 0

        if (imgName.isNotEmpty()) {
            resourceId = ctx.resources.getIdentifier(imgName, "drawable", ctx.packageName)
        }

        if (resourceId != 0) {
            holder.b.ivThumb.setImageResource(resourceId)
        } else {
            // Imagen por defecto si falla o no hay imagen
            holder.b.ivThumb.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun getItemCount(): Int = items.size

    fun update(newList: List<Purchase>) {
        items = newList
        notifyDataSetChanged()
    }
}