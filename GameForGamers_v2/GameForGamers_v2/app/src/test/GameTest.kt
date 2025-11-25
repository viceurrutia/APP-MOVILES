package com.example.gameforgamers

import com.example.gameforgamers.model.Game
import org.junit.Assert.*
import org.junit.Test

class GameTest {

    private fun baseGame(
        id: Int = 1,
        title: String = "Zelda TOTK",
        price: String = "$59.990",
        drawableName: String = "zelda",
        genre: String = "Aventura",
        durationHours: Int = 60,
        shortDesc: String = "Juego de aventura",
        originalPrice: String? = null,
        discountPercent: Int = 0
    ): Game {
        return Game(
            id = id,
            title = title,
            price = price,
            drawableName = drawableName,
            genre = genre,
            durationHours = durationHours,
            shortDesc = shortDesc,
            originalPrice = originalPrice,
            discountPercent = discountPercent
        )
    }

    @Test
    fun `isOffer debe ser false cuando no hay originalPrice o descuento 0`() {
        val g1 = baseGame(originalPrice = null, discountPercent = 20)
        val g2 = baseGame(originalPrice = "$59.990", discountPercent = 0)

        assertFalse(g1.isOffer())
        assertFalse(g2.isOffer())
        assertNull(g1.offerPrice())
        assertNull(g2.offerPrice())
    }

    @Test
    fun `isOffer debe ser true cuando hay originalPrice y descuento mayor a 0`() {
        val g = baseGame(originalPrice = "$59.990", discountPercent = 20)

        assertTrue(g.isOffer())
    }

    @Test
    fun `offerPrice debe calcular correctamente el precio con descuento`() {
        // original: $10.000, descuento 20% -> 8.000
        val g = baseGame(price = "$10.000", originalPrice = "$10.000", discountPercent = 20)

        val offer = g.offerPrice()
        assertNotNull(offer)
        assertEquals("$8.000", offer)
    }

    @Test
    fun `offerPrice debe devolver null si los datos son inválidos`() {
        // originalPrice inválido (no se puede parsear)
        val g = baseGame(price = "abc", originalPrice = "xyz", discountPercent = 50)

        val offer = g.offerPrice()
        assertNull(offer)
    }
}
