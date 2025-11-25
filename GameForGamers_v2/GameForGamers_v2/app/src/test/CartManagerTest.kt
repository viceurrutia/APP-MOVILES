package com.example.gameforgamers

import com.example.gameforgamers.model.Game
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartManagerTest {

    private fun game(
        id: Int,
        price: String,
        title: String = "Juego $id"
    ): Game {
        return Game(
            id = id,
            title = title,
            price = price,
            drawableName = "zelda",
            genre = "Aventura",
            durationHours = 10,
            shortDesc = "Test",
            originalPrice = null,
            discountPercent = 0
        )
    }

    @Before
    fun setUp() {
        // Muy importante limpiar el carrito antes de cada test
        CartManager.clear()
    }

    @Test
    fun `add debe aumentar la cantidad del juego en el carrito`() {
        val g = game(id = 1, price = "$10.000")

        CartManager.add(g)
        CartManager.add(g)

        val all = CartManager.all()
        assertEquals(1, all.size)
        val (gameInCart, quantity) = all[0]
        assertEquals(g.id, gameInCart.id)
        assertEquals(2, quantity)
    }

    @Test
    fun `dec debe disminuir la cantidad y eliminar si llega a 0 o 1`() {
        val g = game(id = 1, price = "$10.000")

        CartManager.add(g) // q = 1
        CartManager.add(g) // q = 2

        CartManager.dec(g) // q = 1, debería eliminar
        val all = CartManager.all()
        assertTrue(all.isEmpty())
    }

    @Test
    fun `remove debe sacar el juego del carrito`() {
        val g1 = game(id = 1, price = "$10.000")
        val g2 = game(id = 2, price = "$20.000")

        CartManager.add(g1)
        CartManager.add(g2)

        CartManager.remove(g1)

        val all = CartManager.all()
        assertEquals(1, all.size)
        assertEquals(2, all[0].first.id)
    }

    @Test
    fun `total debe calcular correctamente el valor del carrito sin oferta`() {
        val g1 = game(id = 1, price = "$10.000") // 10.000
        val g2 = game(id = 2, price = "$5.000")  // 5.000

        CartManager.add(g1) // 1 x 10.000
        CartManager.add(g1) // 2 x 10.000 = 20.000
        CartManager.add(g2) // 1 x 5.000 = 5.000

        val total = CartManager.total()
        assertEquals(25000, total)
    }

    @Test
    fun `total debe usar offerPrice cuando el juego tiene oferta`() {
        val g = Game(
            id = 1,
            title = "Juego oferta",
            price = "$10.000",
            drawableName = "zelda",
            genre = "Aventura",
            durationHours = 10,
            shortDesc = "Test oferta",
            originalPrice = "$10.000",
            discountPercent = 50  // debería quedar en $5.000
        )

        CartManager.add(g) // 1 x 5.000
        CartManager.add(g) // 2 x 5.000 = 10.000

        val total = CartManager.total()
        assertEquals(10000, total)
    }
}
