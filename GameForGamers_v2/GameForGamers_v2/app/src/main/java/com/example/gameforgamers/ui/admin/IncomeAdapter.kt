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
        val b = ItemIncomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]

        // Muestra el correo del cliente y la fecha
        holder.b.tvMonthYear.text = "${p.customerEmail}\n${p.date ?: "Fecha desconocida"}"

        // Muestra el total
        holder.b.tvAmount.text = "$" + "%,d".format(p.totalAmount).replace(",", ".")
    }

    override fun getItemCount(): Int = items.size

    fun update(newList: List<Purchase>) {
        items = newList
        notifyDataSetChanged()
    }
}