package com.example.gameforgamers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

 private lateinit var b: ActivityCartBinding
 private lateinit var adapter: CartAdapter

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  b = ActivityCartBinding.inflate(layoutInflater)
  setContentView(b.root)

  adapter = CartAdapter(
   CartManager.all().toMutableList(),
   onAdd = { p ->
    CartManager.add(p.first)
    refresh()
   },
   onDec = { p ->
    CartManager.dec(p.first)
    refresh()
   },
   onRemove = { p ->
    CartManager.remove(p.first)
    refresh()
   }
  )

  b.recyclerCart.layoutManager = LinearLayoutManager(this)
  b.recyclerCart.adapter = adapter
  updateTotal()

  // 🔹 AHORA: ir al formulario de compra
  b.btnFinish.setOnClickListener {
   if (CartManager.all().isEmpty()) {
    Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
    return@setOnClickListener
   }

   val i = Intent(this, PurchaseActivity::class.java)
   startActivity(i)
  }
 }

 override fun onResume() {
  super.onResume()
  refresh()
 }

 private fun refresh() {
  adapter.update(CartManager.all().toMutableList())
  updateTotal()
 }

 private fun updateTotal() {
  val tot = CartManager.total()
  val s = "$" + "%,d".format(tot).replace(",", ".")
  b.tvTotal.text = "Total: $s"
 }
}
