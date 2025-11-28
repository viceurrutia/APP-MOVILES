package com.example.gameforgamers.data1

import com.example.gameforgamers.model.Income

object IncomeRepository {

    // Datos de ejemplo: puedes ajustar montos y meses como quieras
    private val incomes = listOf(
        Income(2023, "Octubre", 3500000),
        Income(2023, "Noviembre", 4200000),
        Income(2023, "Diciembre", 5100000),
        Income(2024, "Enero", 3800000),
        Income(2024, "Febrero", 4000000),
        Income(2024, "Marzo", 4500000)
    )

    fun all(): List<Income> = incomes
}