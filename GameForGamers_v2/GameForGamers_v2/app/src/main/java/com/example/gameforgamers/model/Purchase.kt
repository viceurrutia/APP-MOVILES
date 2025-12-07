package com.example.gameforgamers.model

import java.io.Serializable

data class Purchase(
    val id: Long = 0,
    val date: String? = null, // El backend pondrá la fecha automática
    val totalAmount: Int,
    val customerEmail: String
) : Serializable