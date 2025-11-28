package com.example.gameforgamers.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameforgamers.databinding.ItemIncomeBinding
import com.example.gameforgamers.model.Income

class IncomeAdapter(
    private val items: List<Income>
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
        val income = items[position]
        holder.b.tvMonthYear.text = "${income.month} ${income.year}"
        holder.b.tvAmount.text = "$" + "%,d".format(income.amount).replace(",", ".")
    }

    override fun getItemCount(): Int = items.size
}