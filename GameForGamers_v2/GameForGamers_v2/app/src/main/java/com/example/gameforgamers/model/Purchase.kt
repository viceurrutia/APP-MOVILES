package com.example.gameforgamers.model

import java.io.Serializable

data class Purchase(
    val id: Long = 0,
    val date: String? = null,
    val totalAmount: Int,
    val customerEmail: String,
    // 🆕 Nuevos campos (con valor por defecto por si acaso)
    val itemsInfo: String = "",
    val imageCode: String = ""
) : Serializable