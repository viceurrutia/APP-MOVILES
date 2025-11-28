package com.example.gameforgamers

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gameforgamers.databinding.ActivityPurchaseBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class PurchaseActivity : AppCompatActivity() {

    private lateinit var b: ActivityPurchaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Spinner: métodos de pago
        val paymentMethods = listOf(
            "Seleccionar...",
            "Tarjeta de crédito",
            "Tarjeta de débito",
            "Transferencia",
            "Efectivo"
        )
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            paymentMethods
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spPaymentMethod.adapter = adapter

        // Fecha: abrir DatePicker al tocar el EditText
        b.etDate.setOnClickListener {
            showDatePicker()
        }

        // Botón confirmar compra
        b.btnConfirmPurchase.setOnClickListener {
            validateAndConfirm()
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(
            this,
            { _, y, m, d ->
                val dateStr = "%02d/%02d/%04d".format(d, m + 1, y)
                b.etDate.setText(dateStr)
            },
            year,
            month,
            day
        )
        picker.show()
    }

    // 🔹 función para vibrar cuando la compra se completa
    private fun vibrateSuccess() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vm.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(200)
        }
    }

    private fun validateAndConfirm() {
        val name = b.etName.text.toString().trim()
        val address = b.etAddress.text.toString().trim()
        val email = b.etEmail.text.toString().trim()
        val phone = b.etPhone.text.toString().trim()
        val date = b.etDate.text.toString().trim()
        val paymentMethod = b.spPaymentMethod.selectedItem.toString()
        val isPickup = b.rbPickup.isChecked
        val isDelivery = b.rbDelivery.isChecked
        val termsAccepted = b.cbTerms.isChecked

        // Nombre: mínimo 3 letras y solo letras/espacios
        if (name.isEmpty()) {
            b.etName.error = "Ingresa tu nombre"
            return
        }
        if (name.length < 3) {
            b.etName.error = "Nombre demasiado corto"
            return
        }
        if (!name.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"))) {
            b.etName.error = "Solo letras y espacios"
            return
        }

        // Dirección
        if (address.isEmpty()) {
            b.etAddress.error = "Ingresa tu dirección"
            return
        }
        if (address.length < 5) {
            b.etAddress.error = "Dirección demasiado corta"
            return
        }

        // Email
        if (email.isEmpty()) {
            b.etEmail.error = "Ingresa tu correo"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            b.etEmail.error = "Correo no válido"
            return
        }

        // Teléfono: solo números y largo mínimo
        if (phone.isEmpty()) {
            b.etPhone.error = "Ingresa tu teléfono"
            return
        }
        if (!phone.matches(Regex("^[0-9]+$"))) {
            b.etPhone.error = "Solo números"
            return
        }
        if (phone.length < 8) {
            b.etPhone.error = "Teléfono inválido"
            return
        }

        // Fecha: no vacía y no pasada (formato dd/MM/yyyy)
        if (date.isEmpty()) {
            b.etDate.error = "Selecciona una fecha"
            return
        } else {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                sdf.isLenient = false
                val selectedDate = sdf.parse(date)
                val today = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time

                if (selectedDate.before(today)) {
                    b.etDate.error = "La fecha no puede ser pasada"
                    return
                }
            } catch (e: Exception) {
                b.etDate.error = "Fecha no válida"
                return
            }
        }

        // Método de pago
        if (paymentMethod == "Seleccionar...") {
            Toast.makeText(this, "Selecciona un método de pago", Toast.LENGTH_SHORT).show()
            return
        }

        // Tipo de entrega
        if (!isPickup && !isDelivery) {
            Toast.makeText(this, "Selecciona tipo de entrega", Toast.LENGTH_SHORT).show()
            return
        }

        // Términos
        if (!termsAccepted) {
            Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val entrega = if (isPickup) "Retiro en tienda" else "Envío a domicilio"

        Toast.makeText(
            this,
            "Compra realizada con éxito\n$entrega - $paymentMethod\nFecha: $date",
            Toast.LENGTH_LONG
        ).show()

        // 🔹 Vibración de éxito (recurso nativo)
        vibrateSuccess()

        // Vaciar carrito y cerrar
        CartManager.clear()
        finish()
    }
}
