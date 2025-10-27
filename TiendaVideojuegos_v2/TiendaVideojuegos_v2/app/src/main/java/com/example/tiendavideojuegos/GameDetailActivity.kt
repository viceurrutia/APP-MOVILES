package com.example.tiendavideojuegos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendavideojuegos.databinding.ActivityGameDetailBinding

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: ""
        val price = intent.getDoubleExtra("price", 0.0)
        val image = intent.getStringExtra("image") ?: ""
        val desc = intent.getStringExtra("description") ?: ""

        binding.tvTitle.text = title
        binding.tvPrice.text = String.format("$%.2f", price)
        binding.tvDescription.text = desc

        val resId = resources.getIdentifier(image, "drawable", packageName)
        if (resId != 0) binding.ivGame.setImageResource(resId)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
