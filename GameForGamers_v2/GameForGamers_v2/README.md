# Game For Gamers

**Proyecto**: Game For Gamers — Tienda de videojuegos móvil (Android + Spring Boot).
Este proyecto implementa un sistema completo de ventas, gestión de stock, roles de usuario y sincronización con servidor.

---

## 📱 Tecnologías

-   **Android (Kotlin)**: App nativa con arquitectura MVVM y ViewBinding.
-   **Spring Boot (Java)**: Backend REST API con base de datos H2 embebida.
-   **Room Database**: Persistencia local para "Favoritos" (Funciona Offline).
-   **Retrofit + Gson**: Cliente HTTP para conectar con la API.
-   **Recursos Nativos**:
    -   📸 **Cámara/Galería**: Para foto de perfil.
    -   📳 **Vibración**: Feedback háptico en compras.
    -   📞 **Teléfono**: Intent para llamadas de soporte.
-   **JUnit**: Pruebas unitarias integradas.

---

## 👥 Roles y Credenciales (Login)

El sistema cuenta con 4 niveles de acceso diferenciados:

| Rol | Correo (User) | Contraseña (Pass) | Permisos |
| :--- | :--- | :--- | :--- |
| **Dueño (Admin 1)** | `admin@admin.com` | `admin123` | Control total: Ver Ventas (Ingresos) y Crear Productos. |
| **Editor (Admin 2)** | `admin2@admin.com` | `admin1234` | Gestión de Stock: Editar y Eliminar juegos. |
| **Soporte (Admin 3)** | `soporte@games.cl` | `soporte123` | Auditoría: Ver listado de Usuarios registrados (Solo lectura). |
| **Cliente** | *(Registro libre)* | *(Cualquiera)* | Comprar, ver perfil, favoritos y reseñas. |

---

## 🚀 Funcionalidades Clave

1.  **Registro y Login Real**: Los usuarios se guardan en el Backend y se sincronizan.
2.  **Gestión de Perfil**: Foto de avatar usando **Cámara o Galería**.
3.  **Catálogo y Ofertas**: Consumo de API `/api/games`.
4.  **Carrito de Compras**: Proceso de venta real que descuenta stock en el servidor.
6.  **Panel de Administración Dinámico**: La interfaz cambia según si entra el Dueño, el Editor o el Soporte.
7.  **APK Firmada**: Proyecto listo para distribución (Release).

---

## 🛠️ Backend y Base de Datos (H2)

El backend utiliza una base de datos en memoria (H2). Para ver las tablas (Juegos, Usuarios, Ventas) en tiempo real:

1.  Ejecuta el proyecto `games-service`.
2.  Abre en tu navegador: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
3.  Ingresa estos datos exactos:
    -   **Driver Class**: `org.h2.Driver`
    -   **JDBC URL**: `jdbc:h2:mem:gamesdb`
    -   **User Name**: `sa`
    -   **Password**: *(Dejar vacío)*
4.  Click en **Connect**.

### Endpoints Principales (API):
-   `GET /api/games` (Ver juegos)
-   `GET /api/users` (Ver usuarios registrados)
-   `POST /api/purchases` (Procesar compra)
-   `GET /api/reviews` (Ver reseñas)

---

## 📂 Estructura del Entregable

-   `/app`: Código fuente Android.
    -   Incluye archivo `llave_juegos.jks` (Firma digital) en la raíz.
-   `/games-service`: Código fuente Backend (Spring Boot).
-   `app-release.apk`: Aplicación compilada y firmada lista para instalar.

---

## ⚙️ Ejecución del Proyecto

1.  **Backend**: Abrir `games-service` en IntelliJ y dar Play ▶️.
2.  **Android**:
    -   Abrir en Android Studio.
    -   Verificar IP en `GameBackendRepository.kt` (usar `10.0.2.2` para emulador).
    -   Ejecutar en emulador o instalar `app-release.apk`.

---

## Contacto

-   **Desarrollado por**: Vicente Urrutia
-   **Asignatura**: Desarrollo de Aplicaciones Móviles (DSY1105)