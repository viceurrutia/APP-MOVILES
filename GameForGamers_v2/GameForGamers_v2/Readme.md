# 🎮 GameForGamers

**Desarrollado por:**
- Vicente Urrutia
- Ivo Rehbein

---

## 🧩 Descripción del Proyecto

**GameForGamers** es una aplicación móvil desarrollada en **Android Studio** con **Kotlin**, que simula una tienda virtual de videojuegos.  
Permite a los usuarios **registrarse, iniciar sesión, visualizar catálogos y ofertas, añadir juegos al carrito y finalizar una compra.**

Su objetivo es demostrar el desarrollo completo de una aplicación funcional con gestión de usuarios, interfaz moderna y almacenamiento local sin necesidad de conexión a internet.

---

## ⚙️ Funcionalidades Implementadas

### 🔐 Autenticación
- Pantallas de **Inicio de sesión** y **Registro** con validaciones.
- Campos en registro: nombre, apellido, RUT, región, comuna, correo electrónico y contraseña.
- Validación de formato de correo y RUT chileno.
- Persistencia de usuarios mediante **SharedPreferences**.

**Usuarios de prueba:**
- `user1@mail.com` / `1234`
- `user2@mail.com` / `abcd`

---

### 🏠 Navegación
- Menú lateral con secciones:
    - **Inicio**
    - **Ofertas**
    - **Catálogo**
    - **Carrito**
    - **Cerrar sesión**
- Transiciones animadas y navegación fluida entre pantallas.

---

### 🛒 Carrito de Compras
- Añadir, quitar o modificar la cantidad de juegos.
- Visualización del total a pagar.
- Botón **“Finalizar compra”** con mensaje de confirmación ✅ *“Compra realizada”*.

---

### 🕹️ Catálogo y Ofertas
- Catálogo con una lista de videojuegos.
- Sección **Ofertas del momento** con:
    - Precio original tachado.
    - Precio en oferta con descuento y porcentaje visible.
    - Juegos exclusivos de oferta.
- Detalle del juego incluye:
    - Imagen.
    - Género.
    - Duración estimada.
    - Descripción breve.

---

### 💾 Persistencia Local
- Los datos de usuario y sesión se guardan con **SharedPreferences**, evitando pérdida de información al cerrar la app.

---

## 🧠 Tecnologías Utilizadas
- **Kotlin**
- **Android Studio**
- **RecyclerView**
- **ViewBinding**
- **Material Design**
- **SharedPreferences**

---

## 🚀 Pasos para Ejecutar el Proyecto

1. **Descargar o clonar** este repositorio:
   ```bash
   git clone https://github.com/viceurrutia/APP-MOVILES.git
