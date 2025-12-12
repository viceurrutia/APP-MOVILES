package com.example.gameforgamers.model
import java.io.Serializable

data class AppUser(
    val id: Long = 0,
    val name: String,
    val email: String,
    val role: String = "USER"
) : Serializable