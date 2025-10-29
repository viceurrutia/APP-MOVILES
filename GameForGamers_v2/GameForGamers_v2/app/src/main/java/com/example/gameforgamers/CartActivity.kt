package com.example.gameforgamers
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.databinding.ActivityCartBinding
class CartActivity: AppCompatActivity(){
 private lateinit var b:ActivityCartBinding; private lateinit var adapter:CartAdapter
 override fun onCreate(savedInstanceState:Bundle?){ super.onCreate(savedInstanceState); b=ActivityCartBinding.inflate(layoutInflater); setContentView(b.root)
 adapter=CartAdapter(CartManager.all().toMutableList(), onAdd={ p-> CartManager.add(p.first); refresh() }, onDec={ p-> CartManager.dec(p.first); refresh() }, onRemove={ p-> CartManager.remove(p.first); refresh() })
 b.recyclerCart.layoutManager=LinearLayoutManager(this); b.recyclerCart.adapter=adapter; updateTotal()
 b.btnFinish.setOnClickListener{ if(CartManager.all().isEmpty()) return@setOnClickListener; android.app.AlertDialog.Builder(this).setTitle("Compra realizada").setMessage("✅ ¡Gracias por tu compra!").setPositiveButton("OK"){ d,_ -> d.dismiss(); CartManager.clear(); refresh() }.show() }
 }
 override fun onResume(){ super.onResume(); refresh() }
 private fun refresh(){ adapter.update(CartManager.all().toMutableList()); updateTotal() }
 private fun updateTotal(){ val tot=CartManager.total(); val s="$"+"%,d".format(tot).replace(",","."); b.tvTotal.text="Total: "+s }
}