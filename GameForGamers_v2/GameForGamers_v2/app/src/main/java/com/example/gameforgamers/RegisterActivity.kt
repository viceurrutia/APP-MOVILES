package com.example.gameforgamers
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityRegisterBinding
class RegisterActivity: AppCompatActivity(){
 private lateinit var b:ActivityRegisterBinding
 override fun onCreate(savedInstanceState:Bundle?){ super.onCreate(savedInstanceState); b=ActivityRegisterBinding.inflate(layoutInflater); setContentView(b.root)
 b.btnRegister.setOnClickListener{
  val nombre=b.etNombre.text.toString().trim(); val apellido=b.etApellido.text.toString().trim()
  val rut=b.etRut.text.toString().trim(); val region=b.etRegion.text.toString().trim(); val comuna=b.etComuna.text.toString().trim()
  val email=b.etEmail.text.toString().trim(); val pass=b.etPassword.text.toString()
  if(nombre.isEmpty()||apellido.isEmpty()||rut.isEmpty()||region.isEmpty()||comuna.isEmpty()||email.isEmpty()||pass.length<6){ Toast.makeText(this,"Completa todos los campos (contraseña min 6)",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
  val emailRx=Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"); if(!emailRx.matches(email)){ Toast.makeText(this,"Correo inválido",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
  if(!isValidRut(rut)){ Toast.makeText(this,"RUT inválido",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
  Prefs.saveUser(this,email,pass); Prefs.saveProfile(this,email, mapOf("nombre" to nombre,"apellido" to apellido,"rut" to rut,"region" to region,"comuna" to comuna))
  Toast.makeText(this,"Usuario registrado",Toast.LENGTH_SHORT).show(); finish()
 }
 }
 private fun isValidRut(rut:String):Boolean{
  val clean=rut.replace(".","").replace("-","").uppercase(); if(clean.length<8) return false
  val body=clean.dropLast(1); val dv=clean.last()
  var sum=0; var mult=2
  for(c in body.reversed()){ sum += (c.digitToInt())*mult; mult = if(mult==7) 2 else mult+1 }
  val res=11-(sum%11); val dvCalc= when(res){ 11 -> '0'; 10 -> 'K'; else -> ('0'+res) }
  return dv==dvCalc
 }
}