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
  val email=b.etEmail.text.toString().trim(); val pass=b.etPassword.text.toString()
  if(nombre.isEmpty()||apellido.isEmpty()||email.isEmpty()||pass.length<6){ Toast.makeText(this,"Revisa los datos (min 6 carac.)",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
  val emailRegex=Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"); if(!emailRegex.matches(email)){ Toast.makeText(this,"Correo inválido",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
  Prefs.saveUser(this,email,pass); Toast.makeText(this,"Usuario registrado",Toast.LENGTH_SHORT).show(); finish()
 }
 }
}
