package com.example.gameforgamers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var b: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnReset.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val newPass = b.etNewPass.text.toString().trim()
            val confirm = b.etConfirmPass.text.toString().trim()

            if (email.isEmpty()) {
                b.etEmail.error = "Ingresa tu correo"
                return@setOnClickListener
            }

            if (newPass.length < 4) {
                b.etNewPass.error = "Mínimo 4 caracteres"
                return@setOnClickListener
            }

            if (newPass != confirm) {
                b.etConfirmPass.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }

            val ok = Prefs.updatePassword(this, email, newPass)

            if (ok) {
                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
