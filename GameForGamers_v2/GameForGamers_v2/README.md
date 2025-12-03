# Game For Gamers

**Proyecto**: Game For Gamers --- tienda de videojuegos (Android +
Spring Boot).\
Muestra catálogo y ofertas, carrito y proceso de compra, administración
de juegos y reseñas.

------------------------------------------------------------------------

## Tecnologías

-   **Android (Kotlin)** --- App móvil (Activities, RecyclerView,
    Retrofit, ViewBinding).
-   **Spring Boot (Java + JPA)** --- Backend REST con H2 / base
    embebida.
-   **Retrofit + Gson** --- Cliente HTTP en Android.
-   **JUnit** --- Tests básicos incluidos (CartManager, Game).

------------------------------------------------------------------------

## Estructura del repo

-   `/app` --- código fuente Android (Activities, adapters, layouts,
    res).
-   `/games-service` --- backend Spring Boot (entidades, repositorios,
    controllers).
    -   `Game`, `Category`, `Review`
    -   `GameRepository`, `CategoryRepository`, `ReviewRepository`
    -   `GameController`, `CategoryController`, `ReviewController`
    -   `GamesServiceApplication` (semilla de datos: CommandLineRunner)

------------------------------------------------------------------------

## Qué hace la app

-   Registro y login (admin fijo: `admin@admin.com / admin123`).
-   Edición de perfil y recuperación de contraseña.
-   Catálogo y ofertas consumiendo `/api/games`.
-   Detalle del juego (precio con oferta tachada).
-   Carrito y compra con validaciones.
-   Admin: añadir, modificar stock y eliminar juegos.
-   Vibración nativa al añadir al carrito / confirmar compra.

------------------------------------------------------------------------

## Requisitos

-   Java 17+
-   Android Studio
-   Emulador o dispositivo Android
-   Backend escuchando en `http://10.0.2.2:8080` o IP local

------------------------------------------------------------------------

## Backend --- ejecución

1.  Abrir `games-service` en IntelliJ/STS.
2.  Ejecutar `GamesServiceApplication`.
3.  Endpoints:
    -   `GET /api/games`
    -   `GET /api/games/{id}`
    -   `POST /api/games`
    -   `PUT /api/games/{id}`
    -   `DELETE /api/games/{id}`
    -   `GET /api/categories`
    -   `GET /api/reviews`
    -   `GET /api/reviews/game/{id}`

------------------------------------------------------------------------

## App Android --- ejecución

1.  Abrir el proyecto en Android Studio.
2.  Configurar `GameBackendRepository` con:
    -   `http://10.0.2.2:8080` (emulador)
    -   `http://<IP-PC>:8080` (dispositivo)
3.  Ejecutar y probar:
    -   Login / Registro
    -   Catálogo / Ofertas
    -   Carrito y compra
    -   Panel Admin
        http://localhost:8080/api/games
        http://localhost:8080/api/reviews


------------------------------------------------------------------------

## Tests

Ejecutar:

    ./gradlew test

------------------------------------------------------------------------

## Contacto

-   Proyecto ejecutado por: **(Vicente Urrutia)**.
